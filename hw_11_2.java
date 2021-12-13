public class hw_11_2 {

    static Generator cntr = new Generator(100);

    public static synchronized void main(String[] args) {

        TimingProcessor threadA = new TimingProcessor(cntr, "A");
        TimingProcessor threadB = new TimingProcessor(cntr, "B");
        TimingProcessor threadC = new TimingProcessor(cntr, "C");
        TimingProcessor threadD = new TimingProcessor(cntr, "D");
        cntr.start();
        threadA.start();
        threadB.start();
        threadC.start();
        threadD.start();

    }

    public static void fizz() {
        int curNum = cntr.getCounter();
        if (curNum % 3 == 0 && curNum % 5 != 0) {
            System.out.print("fizz, ");
        }
    }

    public static void buzz() {
        int curNum = cntr.getCounter();
        if (curNum % 5 == 0 && curNum % 3 != 0) {
            System.out.print("buzz, ");
        }
    }

    public static void fizzbuzz() {
        int curNum = cntr.getCounter();
        if (curNum % 15 == 0) {
            System.out.print("fizzbuzz, ");
        }
    }

    public static void number() {
        int curNum = cntr.getCounter();
        if (curNum % 3 != 0 && curNum % 5 != 0) {
            System.out.print(curNum + ", ");
        }
    }


    static class Generator extends Thread {
        private int counter = 1;
        private int n;
        private boolean setting = false;

        Generator(int n){
            this.n = n;
        }

        @Override
        public void run() {
            do {
                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                setCounter(counter + 1);
            } while (counter<n);
        }

        public synchronized int getCounter() {
            while (setting) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return counter;
        }

        public synchronized void setCounter(int t) {
            setting = true;
            this.counter = t;
            setting = false;
            notifyAll();
        }
    }

    static class TimingProcessor extends Thread {
        Generator counter;
        String threadType;

        public TimingProcessor(Generator t, String threadType) {
            this.counter = t;
            this.threadType = threadType;
        }

        @Override
        public void run() {
            synchronized (counter) {
                while (true) {
                    switch (threadType) {
                        case ("A"):
                            fizz();
                            break;
                        case ("B"):
                            buzz();
                            break;
                        case ("C"):
                            fizzbuzz();
                            break;
                        case ("D"):
                            number();
                            break;
                        default:
                            System.out.println("Unknown type of Thread");
                    }
                    try {
                        if (!counter.isAlive()) break;
                        counter.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }
}
