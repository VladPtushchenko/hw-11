public class hw_11_1 {

    public static synchronized void main(String[] args) {
        Timer timer = new Timer();
        timer.start();

        Message5 message5 = new Message5(timer);
        message5.start();

    }
}

class Timer extends Thread{
    private int time = 0;
    private boolean setting = false;

    public void run(){
        while(true) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            setTime(time + 1);
            System.out.println(time);
        }
    }
    public synchronized int getTime(){
        while(setting){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return time;
    }
    public synchronized void setTime(int t){
        setting = true;
        this.time = t;
        setting = false;
        notifyAll();
    }
}

class Message5 extends Thread{
    Timer timer;
    public Message5(Timer t){
        this.timer = t;
    }

    public void run(){
        synchronized(timer){
            while(true){
                try {
                    timer.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(timer.getTime() % 5 == 0){
                    System.out.println("Прошло 5 секунд");
                }

            }
        }
    }
}
