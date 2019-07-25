package com.fancy.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.fancy.myapplication.bean.Person;
import com.fancy.myapplication.bean.Student;
import com.fancy.myapplication.bean.Translation;
import com.fancy.myapplication.util.GetRequest_Interface;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author pengkuanwang
 * @date 2019-07-17
 */
public class ObservableActivity extends Activity {
    public static final String TAG = "ObservableActivity";
    TextView countTv;
    // 设置变量 = 模拟轮询服务器次数
    private int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observable);
        countTv = findViewById(R.id.countTv);
        // 步骤1：创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                .build();

        // 步骤2：创建 网络请求接口 的实例
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

        // 步骤3：采用Observable<...>形式 对 网络请求 进行封装
        final Observable<Translation> observable2 = request.getCall();
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 步骤4：发送网络请求 & 通过repeatWhen（）进行轮询
                observable2.repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
                    @Override
                    // 在Function函数中，必须对输入的 Observable<Object>进行处理，此处使用flatMap操作符接收上游的数据
                    public ObservableSource<?> apply(@NonNull Observable<Object> objectObservable) throws Exception {
                        // 将原始 Observable 停止发送事件的标识（Complete（） /  Error（））转换成1个 Object 类型数据传递给1个新被观察者（Observable）
                        // 以此决定是否重新订阅 & 发送原来的 Observable，即轮询
                        // 此处有2种情况：
                        // 1. 若返回1个Complete（） /  Error（）事件，则不重新订阅 & 发送原来的 Observable，即轮询结束
                        // 2. 若返回其余事件，则重新订阅 & 发送原来的 Observable，即继续轮询
                        return objectObservable.flatMap(new Function<Object, ObservableSource<?>>() {
                            @Override
                            public ObservableSource<?> apply(@NonNull Object throwable) throws Exception {

                                // 加入判断条件：当轮询次数 = 5次后，就停止轮询
                                if (i > 3) {
                                    // 此处选择发送onError事件以结束轮询，因为可触发下游观察者的onError（）方法回调
                                    return Observable.error(new Throwable("轮询结束"));
                                }
                                // 若轮询次数＜4次，则发送1Next事件以继续轮询
                                // 注：此处加入了delay操作符，作用 = 延迟一段时间发送（此处设置 = 2s），以实现轮询间间隔设置
                                return Observable.just(1).delay(2000, TimeUnit.MILLISECONDS);
                            }
                        });

                    }
                }).subscribeOn(Schedulers.io())               // 切换到IO线程进行网络请求
                        .observeOn(AndroidSchedulers.mainThread())  // 切换回到主线程 处理请求结果
                        .subscribe(new Observer<Translation>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                            }

                            @Override
                            public void onNext(Translation result) {
                                // e.接收服务器返回的数据
                                result.show();
                                i++;
                            }

                            @Override
                            public void onError(Throwable e) {
                                // 获取轮询结束信息
                                Log.d(TAG, e.toString());
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            }
        });
        findViewById(R.id.buttonIntervalRange).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                 * 步骤1：采用interval（）延迟发送
                 * 注：此处主要展示无限次轮询，若要实现有限次轮询，仅需将interval（）改成intervalRange（）即可
                 **/
                Observable.interval(2, 1, TimeUnit.SECONDS)
                        // 参数说明：
                        // 参数1 = 第1次延迟时间；
                        // 参数2 = 间隔时间数字；
                        // 参数3 = 时间单位；
                        // 该例子发送的事件特点：延迟2s后发送事件，每隔1秒产生1个数字（从0开始递增1，无限个）

                        /*
                         * 步骤2：每次发送数字前发送1次网络请求（doOnNext（）在执行Next事件前调用）
                         * 即每隔1秒产生1个数字前，就发送1次网络请求，从而实现轮询需求
                         **/
                        .doOnNext(new Consumer<Long>() {
                            @Override
                            public void accept(Long integer) throws Exception {
                                Log.d(TAG, "第 " + integer + " 次轮询");

                                /*
                                 * 步骤3：通过Retrofit发送网络请求
                                 **/
                                // a. 创建Retrofit对象
                                Retrofit retrofit = new Retrofit.Builder()
                                        .baseUrl("http://fy.iciba.com/") // 设置 网络请求 Url
                                        .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                                        .build();

                                // b. 创建 网络请求接口 的实例
                                GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

                                // c. 采用Observable<...>形式 对 网络请求 进行封装
                                Observable<Translation> observable = request.getCall();
                                // d. 通过线程切换发送网络请求
                                observable.subscribeOn(Schedulers.io())               // 切换到IO线程进行网络请求
                                        .observeOn(AndroidSchedulers.mainThread())  // 切换回到主线程 处理请求结果
                                        .subscribe(new Observer<Translation>() {
                                            @Override
                                            public void onSubscribe(Disposable d) {
                                                log("onSubscribe");
                                            }

                                            @Override
                                            public void onNext(Translation result) {
                                                // e.接收服务器返回的数据
                                                result.show();
                                            }

                                            @Override
                                            public void onError(Throwable e) {
                                                Log.d(TAG, "请求失败");
                                            }

                                            @Override
                                            public void onComplete() {

                                            }
                                        });

                            }
                        }).subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        log("onSubscribe链接");
                    }

                    @Override
                    public void onNext(Long value) {
                        log(String.valueOf(value));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "对Error事件作出响应");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "对Complete事件作出响应");
                    }
                });


            }
        });
        findViewById(R.id.buttonInterval).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable.interval(3, 1, TimeUnit.SECONDS)
                        .subscribe(new Observer<Long>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                log("开始连接");
                            }

                            @Override
                            public void onNext(Long aLong) {
                                countTv.setText(String.valueOf(aLong));
                            }

                            @Override
                            public void onError(Throwable e) {

                            }

                            @Override
                            public void onComplete() {
                                log("完成");
                            }
                        });

            }
        });
        findViewById(R.id.buttonTimer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable<Long> timer = Observable.timer(3, TimeUnit.SECONDS);
                timer.subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        log("开始连接");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        log(String.valueOf(aLong));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        log("完成");
                    }
                });
            }
        });
        findViewById(R.id.buttonDefer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable<Person> defer = Observable.defer(new Callable<ObservableSource<Person>>() {
                    @Override
                    public ObservableSource<Person> call() throws Exception {
                        return Observable.just(new Person("name", 34));
                    }
                });
                Observer<Person> observer = new Observer<Person>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        log("开始连接");
                    }

                    @Override
                    public void onNext(Person person) {
                        log(person.name);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                };
                defer.subscribe(observer);
            }
        });
        findViewById(R.id.buttonEmpty).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable<Person> empty = Observable.empty();
                empty.subscribe(new Observer<Person>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        log("开始连接");
                    }

                    @Override
                    public void onNext(Person person) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        log("完成");
                    }
                });
            }
        });
        findViewById(R.id.buttonFromIterable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person person = new Person("name", 1);
                Person person1 = new Person("name1", 2);
                Person person2 = new Person("name2", 3);
                ArrayList<Person> list = new ArrayList<>();
                list.add(person);
                list.add(person1);
                list.add(person2);
                Observable.fromIterable(list).subscribe(new Observer<Person>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        log("开始连接");
                    }

                    @Override
                    public void onNext(Person person) {
                        log(person.name);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        log("完成");
                    }
                });
            }
        });
        findViewById(R.id.buttonFromArray).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person person = new Person("name", 1);
                Person person1 = new Person("name1", 2);
                Person person2 = new Person("name2", 3);
                Person[] p = new Person[]{person, person1, person2};
                Observable.fromArray(p).subscribe(new Observer<Person>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        log("开始连接");
                    }

                    @Override
                    public void onNext(Person person) {
                        log(person.name);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        log("完成");
                    }
                });
            }
        });

        final Observable<Student> observable = Observable.create(new ObservableOnSubscribe<Student>() {
            @Override
            public void subscribe(ObservableEmitter<Student> emitter) {
                emitter.onNext(new Person("name", 1));
                emitter.onNext(new Person("name1", 2));
                emitter.onNext(new Person("name2", 3));
                emitter.onComplete();
            }
        });
        final Observer<Student> observer = new Observer<Student>() {
            @Override
            public void onSubscribe(Disposable d) {
                log("开始连接");
            }

            @Override
            public void onNext(Student student) {
                Person person = (Person) student;
                log(person.name);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {
                log("完成");
            }
        };
        findViewById(R.id.buttonCreate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                observable.subscribe(observer);
            }
        });
        findViewById(R.id.buttonJust).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Observable<Integer> just = Observable.just(1, 2, 3, 4);
                just.subscribe(new Observer<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        log("开始连接");
                    }

                    @Override
                    public void onNext(Integer integer) {
                        log(integer + "");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        log("完成");
                    }
                });
            }
        });
    }

    public void log(String message) {
        Log.d(TAG, message);
    }
}
