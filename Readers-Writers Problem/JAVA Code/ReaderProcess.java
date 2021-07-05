public class ReaderProcess extends Thread{
    Semaphore mutex;
    Semaphore reader_allowed;
    Semaphore writer_allowed;
    Semaphore writer_finished;

    String threadName;

    public ReaderProcess(String threadName){
        super(threadName);
        this.threadName = threadName;
        this.mutex = ProcessController.mutex;
        this.reader_allowed = ProcessController.reader_allowed;
        this.writer_allowed = ProcessController.writer_allowed;
        this.writer_finished = ProcessController.writer_finished;
    }

    @Override
    public void run() {
        try{
            mutex.waiting();
            System.out.println(this.getName() + " has acquired mutex Initial");
            
            if(ProcessController.getActiveWriter() + ProcessController.getPrimaryWriter() + ProcessController.getWaitingWriter() == 0){
                ProcessController.incActiveReader();
                System.out.println(this.getName() + " has become active reader");
                reader_allowed.signal();
            }else{
                ProcessController.incWaitingReader();
                System.out.println(this.getName() + " has become waiting reader");
            }

            System.out.println(this.getName() + " has released mutex Initial");
            mutex.signal();

            reader_allowed.waiting();

            System.out.println("\tReader Entry Into Critical Section: " + this.getName());
            System.out.println("\tValue read by " + this.getName() + " " + Shared.sharedCount);
            System.out.println("\tReader Exit From Critical Section: " + this.getName());

            mutex.waiting();
            System.out.println(this.getName() + " has acquired mutex Final");
            ProcessController.decActiveReader();
            if(ProcessController.getActiveReader() == 0 && ProcessController.getWaitingWriter() > 0){
                ProcessController.setActiveWriter(1);
                writer_allowed.signal();
                ProcessController.setPrimaryWriter(ProcessController.getWaitingWriter() - 1);
                ProcessController.setWaitingWriter(0);
                System.out.println(this.getName() + " has released mutex Final and updated active writer, primary writer and waiting writer count");
            }else{
                System.out.println(this.getName() + " has released mutex Final and there was nothing to update for Writer Processes");
            }
            mutex.signal();
        }catch(Exception e){
            System.out.println(e);
        }
    }
}
