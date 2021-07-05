import java.util.concurrent.Semaphore;

class Shared{
    public static int sharedCount = 0;
}
public class TemplateOfProcess {
    private static int active_writer_count;
    private static int waiting_writer_count;
    private static int primary_writer_count;

    private static int active_reader_count;
    private static int waiting_reader_count;

    static Semaphore mutex;
    static Semaphore reader_allowed;
    static Semaphore writer_allowed;
    static Semaphore writer_finished;

    public TemplateOfProcess(){
        active_writer_count = 0;
        waiting_writer_count = 0;
        primary_writer_count = 0;

        active_reader_count = 0;
        waiting_reader_count = 0;

        mutex = new Semaphore(1, true);
        reader_allowed = new Semaphore(1, true);
        writer_allowed = new Semaphore(1, true);
        writer_finished = new Semaphore(1, true);

        try {
            reader_allowed.acquire();
            writer_allowed.acquire();
            writer_finished.acquire();   
        }catch (Exception e){
            System.out.println(e);
        }
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
