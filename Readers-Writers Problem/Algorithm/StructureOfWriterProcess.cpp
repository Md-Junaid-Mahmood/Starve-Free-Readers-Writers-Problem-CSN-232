/* Below mentioned is the structure of a Writer Process */
void Writer(){
    wait (*mutex);
    /* Writer process checks whether there is any Reader process that is waiting for execution prior to it */
    /* If No, then if statment is true, otherwise, it is false */
    if(active_reader_count + waiting_reader_count == 0){
        /* If no Writer process is inside critical section then make it as active Writer process */
        /*  Otherwise make it as primary Writer process */
        if(active_writer_count == 0){
            active_writer_count = 1;
            /* Allow active Writer process to access critical section */
            signal (*writer_allowed);
        }else{
            primary_writer_count++;
        }
    }else{
        waiting_writer_count++;
    }
    signal (*mutex);
    wait (*writer_allowed);

    /* Entry to Critical Section */
    /* Writing is Performed Here */
    /* Exit from Critical Section */

    signal (*writer_finished);
    wait (*mutex);
    /* Allow all primary Writer process to access critical section and complete their execution */
    while(primary_writer_count > 0){
        /* Wait until prior Writer process has finished execution of critical section */
        wait (*writer_finished);
        signal (*writer_allowed);
        primary_writer_count--;
    }

    wait (*writer_finished);
    signal (*writer_allowed);

    active_writer_count = 0;

    /* If there are waiting Readers process then allow that group to access critical section and complete their execution */
    while(waitng_reader_count > 0){
        signal (*reader_allowed);
        active_reader_count++;
        waiting_reader_count--;
    } 
    signal (*mutex);
}
