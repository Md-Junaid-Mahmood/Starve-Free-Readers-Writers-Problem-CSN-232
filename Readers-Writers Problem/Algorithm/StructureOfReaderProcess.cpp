/* Below mentioned is the structure of a Reader Process */
void Readers(){
    wait (*mutex);
    /* Reader process checks whether there is any Writer process that is waiting for execution prior to it */
    /* If No, then if statment is true, otherwise, it is false */
    if(active_writer_count + primary_writer_count + waiting_writer_count == 0){
        active_reader_count++;
        /* Allow Reader process to access critical section */
        signal (*reader_allowed)
    }else{
        waiting_reader_count++;
    }
    signal (*mutex);
    wait (*reader_allowed);

    /* Entry to Critical Section */
    /* Reading is Performed Here */
    /* Exit from Critical Section */

    wait (*mutex);
    active_reader_count--;
    if(active_reader_count == 0 and waiting_writer_count > 0){
        /* If last Reader process in the group has completed its execution and there are waiting Writer Process */
        /* Create any one Writer process as active Writer process and remaining as primary Writer process */
        active_writer_count = 1;
        signal (*writer_allowed);
        primary_writer_count = waiting_writer_count - 1;
    }
    signal (*mutex);
}