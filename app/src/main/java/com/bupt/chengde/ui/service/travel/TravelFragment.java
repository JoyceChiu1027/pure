package com.bupt.chengde.ui.service.travel;

import android.os.Bundle;
import android.os.Environment;

import com.bupt.chengde.base.LazyFragment;
import com.bupt.chengde.control.CodeConstants;
import com.bupt.chengde.util.LogUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;

/**
 * Created by joycezhao on 16/10/24.
 */

public class TravelFragment extends LazyFragment {
    Student[] students = new Student[5];
    private int tabIndex;

    public static TravelFragment newInstance(int tabIndex, boolean isLazyLoad) {
        Bundle args = new Bundle();
        args.putInt(CodeConstants.TAB_INDEX, tabIndex);
        args.putBoolean(CodeConstants.IS_LAZY_LOAD, isLazyLoad);
        TravelFragment fragment = new TravelFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initData(Bundle bundle) {

    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        // setContentView(R.layout.fragment_tabmain_item);
        tabIndex = getArguments().getInt(CodeConstants.TAB_INDEX);
        // ivContent = (ImageView) findViewById(R.id.iv_content);
        // tvLoading = (TextView) findViewById(R.id.tv_loading);
        getData();
    }

    private void getData() {
        Observable.timer(1500, TimeUnit.MILLISECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe();
        Subscriber<Student> subscriber2 = new Subscriber<Student>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Student student) {
                List<Course> courses = student.getCourses();
                for (int i = 0; i < courses.size(); i++) {
                    LogUtil.e("flatmap test", "课程名字：" + courses.get(i).getCourseName());
                    //chinesetextview.setText("语文成绩："+courses.get(i).getChinese());
                }
            }
        };
        Subscriber<String> subscriber1 = new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                LogUtil.d(TAG, "s=" + s);
                LogUtil.i(TAG, "线程名：" + Thread.currentThread().getName());
            }
        };
        Subscriber<Course> subscriber3 = new Subscriber<Course>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Course course) {
                LogUtil.e(TAG, "course.getcoursename=" + course.getCourseName());
            }
        };

        Observable.from(students)
                .map(new Func1<Student, String>() {
                    @Override
                    public String call(Student student) {
                        LogUtil.w(TAG, ".map中Func1<Student, String>()的call and threadname=" + Thread.currentThread().getName());
                        return student.getName();
                        //  return null;
                    }
                })
                .subscribe(subscriber1);
        Observable.from(students).subscribe(subscriber2);
        Observable.from(students).flatMap(new Func1<Student, Observable<Course>>() {
            @Override
            public Observable<Course> call(Student student) {
                return Observable.from(student.getCourses());
            }
        }).subscribe(subscriber3);
        Observable observable = Observable.create(new Observable.OnSubscribe<File>() {
            @Override
            public void call(Subscriber<? super File> subscriber) {
                subscriber.onNext(new File(Environment.getExternalStorageDirectory(), "chengde_cache"));
                subscriber.onCompleted();

            }
        });
//        Observable.zip()

    }

    public void setData() {
        String[] names = {"小红", "小明", "小兰", "小雷", "小黄"};
        int[] ages = {11, 12, 13, 14, 15, 16};
        String[] courseNames = {"语文", "数学", "英语", "物理", "化学"};
        String[] courseIds = {"001", "002", "003", "004", "005"};
        List<Course> coursesList = new ArrayList<>();
        for (int j = 0; j < students.length; j++) {
            Course course = new Course();
            course.setCourseName(courseNames[j]);
            course.setCourseId(courseIds[j]);
            coursesList.add(course);
        }
        for (int i = 0; i < students.length; i++) {
            Student student = new Student();
            student.setAge(ages[i]);
            student.setName(names[i]);
            student.setCourses(coursesList);
            students[i] = student;
        }
    }

    class Student {
        int age;
        String name;
        List<Course> courses;

        public int getAge() {
            return age;
        }

        void setAge(int age) {
            this.age = age;
        }

        public List<Course> getCourses() {
            return courses;
        }

        void setCourses(List<Course> courses) {
            this.courses = courses;
        }

        public String getName() {
            return name;
        }

        void setName(String name) {
            this.name = name;
        }

    }

    class Course {
        String courseName;
        String courseId;

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public String getCourseId() {
            return courseId;
        }

        public void setCourseId(String courseId) {
            this.courseId = courseId;
        }
    }
}
