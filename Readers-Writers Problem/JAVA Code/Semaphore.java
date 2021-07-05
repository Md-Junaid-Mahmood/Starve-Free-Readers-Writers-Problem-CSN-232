public class Semaphore {
    int value;
    public Semaphore(int num){
        this.value = num;
    }

    public synchronized void waiting(){
        this.value--;
        if(this.value < 0){
            try {
                wait();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public synchronized void signal(){
        this.value++;
        if(this.value <= 0){
            notify();
        }
    }
}
