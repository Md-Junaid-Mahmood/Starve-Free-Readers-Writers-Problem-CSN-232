/* For the demonstartion purpose Integer item sharedCount of class Shared is considered to be the critical Section */
class Shared{
    public static int sharedCount = 0;
}

public class ProcessController {
    /* Variables for storing details of the Writers Processes */
    private static int active_writer_count;
    private static int waiting_writer_count;
    private static int primary_writer_count;

    /* Variables for storing details of the Readers Processes */
    private static int active_reader_count;
    private static int waiting_reader_count;

    /* Semaphores for achieving necessary synchronisation between Reader & Writer Processes */
    static Semaphore mutex;
    static Semaphore reader_allowed;
    static Semaphore writer_allowed;
    static Semaphore writer_finished;

    public ProcessController(){

        /* Initialising the Variables */
        active_writer_count = 0;
        waiting_writer_count = 0;
        primary_writer_count = 0;

        active_reader_count = 0;
        waiting_reader_count = 0;

        /* Initialising the Semaphores */
        /* 1 means value can be incremented or decremented by one unit only */
        /* true means threads that are waiting on this semaphore will be awaken in same order as the order in which they were kept waiting */
        mutex = new Semaphore(1);
        reader_allowed = new Semaphore(0);
        writer_allowed = new Semaphore(0);
        writer_finished = new Semaphore(0);

        /* Initialising acquiring the semaphores for reader_allowed, writer_allowed and writer_finished semaphore */
    }

    /* For increasing the needed static variables */
    static public void incActiveWriter(){
        active_writer_count++;
    }

    static public void incWaitingWriter(){
        waiting_writer_count++;
    }

    static public void incPrimaryWriter(){
        primary_writer_count++;
    }

    static public void incActiveReader(){
        active_reader_count++;
    }

    static public void incWaitingReader(){
        waiting_reader_count++;
    }

    /* For decreasing the needed static variables */
    static public void decActiveWriter(){
        active_writer_count--;
    }

    static public void decWaitingWriter(){
        waiting_writer_count--;
    }

    static public void decPrimaryWriter(){
        primary_writer_count--;
    }

    static public void decActiveReader(){
        active_reader_count--;
    }

    static public void decWaitingReader(){
        waiting_reader_count--;
    }

    /* For obtaining the needed static variables */
    static public int getActiveWriter(){
        return active_writer_count;
    }

    static public int getWaitingWriter(){
        return waiting_writer_count;
    }

    static public int getPrimaryWriter(){
        return primary_writer_count;
    }

    static public int getActiveReader(){
        return active_reader_count;
    }

    static public int getWaitingReader(){
        return waiting_reader_count;
    }

    /* Set value of static variables */
    static public void setActiveWriter(int num){
        active_writer_count = num;
    }

    static public void setPrimaryWriter(int num){
       primary_writer_count = num;
    }

    static public void setWaitingWriter(int num){
        waiting_writer_count = num;
     }
}
