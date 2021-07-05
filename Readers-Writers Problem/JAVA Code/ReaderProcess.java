import java.util.concurrent.Semaphore;

public class ReaderProcess extends Thread{
    Semaphore mutex;
    Semaphore reader_allowed;
    Semaphore writer_allowed;
    Semaphore writer_finished;

    String threadName;

    public ReaderProcess(String threadName){
        super(threadName);
        this.threadName = threadName;
        this.mutex = TemplateOfProcess.mutex;
        this.reader_allowed = TemplateOfProcess.reader_allowed;
        this.writer_allowed = TemplateOfProcess.writer_allowed;
        this.writer_finished = TemplateOfProcess.writer_finished;
    }

    @Override
    public void run() {
        try{
            mutex.acquire();
            System.out.println(this.getName() + " has acquired mutex Initial");
            
            if(TemplateOfProcess.getActiveWriter() + TemplateOfProcess.getPrimaryWriter() + TemplateOfProcess.getWaitingWriter() == 0){
                TemplateOfProcess.incActiveReader();
                System.out.println(this.getName() + " has become active reader");
                reader_allowed.release();
            }else{
                TemplateOfProcess.incWaitingReader();
                System.out.println(this.getName() + " has become waiting reader");
            }

            System.out.println(this.getName() + " has released mutex Initial");
            mutex.release();

            reader_allowed.acquire();

            System.out.println("\tReader Entry Into Critical Section: " + this.getName());
            System.out.println("\tValue read by " + this.getName() + " " + Shared.sharedCount);
            System.out.println("\tReader Exit From Critical Section: " + this.getName());

            mutex.acquire();
            System.out.println(this.getName() + " has acquired mutex Final");
            TemplateOfProcess.decActiveReader();
            if(TemplateOfProcess.getActiveReader() == 0 && TemplateOfProcess.getWaitingWriter() > 0){
                TemplateOfProcess.setActiveWriter(1);
                writer_allowed.release();
                TemplateOfProcess.setPrimaryWriter(TemplateOfProcess.getWaitingWriter() - 1);
                TemplateOfProcess.setWaitingWriter(0);
                System.out.println(this.getName() + " has released mutex Final and updated active writer, primary writer and waiting writer count");
            }else{
                System.out.println(this.getName() + " has released mutex Final and there was nothing to update for Writer Processes");
            }
            mutex.release();
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
