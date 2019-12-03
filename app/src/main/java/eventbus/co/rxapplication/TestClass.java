package eventbus.co.rxapplication;

import io.reactivex.Observable;

public class TestClass {

    private String name = "Omid Zakeri";

    Observable observable = Observable.just(name);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
