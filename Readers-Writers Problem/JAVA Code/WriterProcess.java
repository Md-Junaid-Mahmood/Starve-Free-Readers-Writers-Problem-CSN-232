public class WriterProcess extends Thread{
    Semaphore mutex;
    Semaphore reader_allowed;
    Semaphore writer_allowed;
    Semaphore writer_finished;

    String threadName;

    public WriterProcess(String threadName){
        super(threadName);
        this.threadName = threadName;
        this.mutex = ProcessController.mutex;
        this.reader_allowed = ProcessController.reader_allowed;
        this.writer_allowed = ProcessController.writer_allowed;
        this.writer_finished = ProcessController.writer_finished;
    }

    @Override
    public void run() {
        try {
            mutex.waiting();
            System.out.println(this.getName() + " has acquired mutex Initial");

            if(ProcessController.getActiveReader() + ProcessController.getWaitingReader() == 0){
                if(ProcessController.getActiveWriter() == 0){
                    ProcessController.setActiveWriter(1);;
                    System.out.println(this.getName() + " has become active writer");
                    writer_allowed.signal();
                }else{
                    ProcessController.incPrimaryWriter();
                    System.out.println(this.getName() + " has become primary writer");
                }
            }else{
                ProcessController.incWaitingWriter();
                System.out.println(this.getName() + " has become waiting writer");
            }
            System.out.println(this.getName() + " has released mutex Initial");
            mutex.signal();
            writer_allowed.waiting();

            System.out.println("\tWriter Entry Into Critical Section: " + this.getName());
            System.out.println("\tValue read by " + this.getName() + " " + Shared.sharedCount);
            Shared.sharedCount++;
            System.out.println("\tValue modified by " + this.getName() + " " + Shared.sharedCount);
            System.out.println("\tWriter Exit From Critical Section: " + this.getName());

            writer_finished.signal();
            mutex.waiting();
            System.out.println(this.getName() + " has acquired mutex Final");
            while(ProcessController.getPrimaryWriter() > 0){
                writer_finished.waiting();
                System.out.println(this.getName() + " has released Primary Writer");
                writer_allowed.signal();
                ProcessController.decPrimaryWriter();
            }
            writer_finished.waiting();
            writer_finished.signal();
        
            ProcessController.setActiveWriter(0);
        
            if(ProcessController.getWaitingReader() == 0){
                System.out.println(this.getName() + " do not have any waiting readers to release");
            }else{
                System.out.println(this.getName() + " has released waiting readers");
            }
            while(ProcessController.getWaitingReader() > 0){
                reader_allowed.signal();
                ProcessController.incActiveReader();
                ProcessController.decWaitingReader();
            }
            
            System.out.println(this.getName() + " has released mutex Final");
            mutex.signal();
        }catch(Exception e) {
            System.out.println(e);
        }
    }
}
