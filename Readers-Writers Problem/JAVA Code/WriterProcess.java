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
            System.out.println(this.getName() + " acquired mutex Initial");

            if(TemplateOfProcess.getActiveReader() + TemplateOfProcess.getWaitingReader() == 0){
                if(TemplateOfProcess.getActiveWriter() == 0){
                    TemplateOfProcess.setActiveWriter(1);;
                    
                    System.out.println(this.getName() + " became active writer");
                    writer_allowed.release();
                }else{
                    TemplateOfProcess.incPrimaryWriter();
                    System.out.println(this.getName() + " became primary writer");
                }
            }else{
                TemplateOfProcess.incWaitingWriter();
                System.out.println(this.getName() + " became waiting writer");
            }
            System.out.println(this.getName() + " released mutex Initial");
            mutex.release();

            writer_allowed.acquire();
            Shared.sharedCount++;
            System.out.println("Writer Entry: " + this.getName() + " " + Shared.sharedCount);
            writer_finished.release();

            mutex.acquire();
            System.out.println(this.getName() + " acquired mutex Final");
            while(TemplateOfProcess.getPrimaryWriter() > 0){
                writer_allowed.release();
                TemplateOfProcess.decPrimaryWriter();
                writer_finished.acquire();
                System.out.println(this.getName() + " released Primary Writer");
            }
        
            TemplateOfProcess.setActiveWriter(0);
        
            while(TemplateOfProcess.getWaitingReader() > 0){
                reader_allowed.release();
                TemplateOfProcess.incActiveReader();
                TemplateOfProcess.decWaitingReader();
                System.out.println(this.getName() + " released waiting readers");
            }
            
            System.out.println(this.getName() + " released mutex Final");
            mutex.release();
        }catch(Exception e) {
            System.out.println(e);
        }
    }
}
