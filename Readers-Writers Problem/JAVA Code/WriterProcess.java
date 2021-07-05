import java.util.concurrent.Semaphore;

public class WriterProcess extends Thread{
    Semaphore mutex;
    Semaphore reader_allowed;
    Semaphore writer_allowed;
    Semaphore writer_finished;

    String threadName;

    public WriterProcess(String threadName){
        super(threadName);
        this.threadName = threadName;
        this.mutex = TemplateOfProcess.mutex;
        this.reader_allowed = TemplateOfProcess.reader_allowed;
        this.writer_allowed = TemplateOfProcess.writer_allowed;
        this.writer_finished = TemplateOfProcess.writer_finished;
    }

    @Override
    public void run() {
        try {
            mutex.acquire();
            System.out.println(this.getName() + " has acquired mutex Initial");

            if(TemplateOfProcess.getActiveReader() + TemplateOfProcess.getWaitingReader() == 0){
                if(TemplateOfProcess.getActiveWriter() == 0){
                    TemplateOfProcess.setActiveWriter(1);;
                    System.out.println(this.getName() + " has become active writer");
                    writer_allowed.release();
                }else{
                    TemplateOfProcess.incPrimaryWriter();
                    System.out.println(this.getName() + " has become primary writer");
                }
            }else{
                TemplateOfProcess.incWaitingWriter();
                System.out.println(this.getName() + " has become waiting writer");
            }
            System.out.println(this.getName() + " has released mutex Initial");
            mutex.release();
            writer_allowed.acquire();

            System.out.println("\tWriter Entry Into Critical Section: " + this.getName());
            System.out.println("\tValue read by " + this.getName() + " " + Shared.sharedCount);
            Shared.sharedCount++;
            System.out.println("\tValue modified by " + this.getName() + " " + Shared.sharedCount);
            System.out.println("\tWriter Exit From Critical Section: " + this.getName());

            writer_finished.release();
            mutex.acquire();
            System.out.println(this.getName() + " has acquired mutex Final");
            while(TemplateOfProcess.getPrimaryWriter() > 0){
                writer_finished.acquire();
                System.out.println(this.getName() + " has released Primary Writer");
                writer_allowed.release();
                TemplateOfProcess.decPrimaryWriter();
            }
            writer_finished.acquire();
            writer_finished.release();
        
            TemplateOfProcess.setActiveWriter(0);
        
            if(TemplateOfProcess.getWaitingReader() == 0){
                System.out.println(this.getName() + " do not have any waiting readers to release");
            }else{
                System.out.println(this.getName() + " has released waiting readers");
            }
            while(TemplateOfProcess.getWaitingReader() > 0){
                reader_allowed.release();
                TemplateOfProcess.incActiveReader();
                TemplateOfProcess.decWaitingReader();
            }
            
            System.out.println(this.getName() + " has released mutex Final");
            mutex.release();
        }catch(Exception e) {
            System.out.println(e);
        }
    }
}
