/* Below mentioned is the pseudo code for the starvation free solution to the classical Readers-Writers problem */
/* This is basically a pseudo code written with the help of text formatting used in C++ */
/* Hence no information about Headers Files are included here */
/* In actual implementation information about Headers file must be included */
/* This pseudo code just displays how the implementation should look like */

/* Below mentioned is the definition of Semaphore */
struct semaphore{
    int value;
    struct process *list;
    /* Each process has various variables such as processID, process stack, data section, heap section and so on */
    /* Thus, it is assumed that struct data type would have been used for representing a process */
    /* This list stores all the processes that are waiting on a particular semaphore */ 
};

/* Below mentioned implementation of wait() operation and signal() operation avoids busy waiting */
/* This is done using block() and wakeup() function */
/* block() function blocks the calling process */
/* wakeup() function awakens a blocked process */
/* It is assumed that block() and wakeup() function are present in the programming language */
/* Thus, their implementation is not mentioned explicitly */

/* Implementation of wait() operation */
void wait(semaphore *S){
    S->value--;
    if(S->value < 0){
        S->list.addProcess(); /* Adds the calling process to the list */
        this.block(); /* Blocks the calling process */
    }
}

/* Implementation of signal() operation */
void signal(semaphore *S){
    S->value++;
    if(S->value <= 0){
        process P = S->list.removeProcess(); /* Removes a process from the list */
        /* The removed process is random in nature */
        /* This means that all processes in the list have equal probability of being removed */
        /* Although other mechanishms for removing the process from the list can be adopted using stack or queue data structure */
        P.wakeup(); /* Awakens the removed process */  
    }
}


/* Below mentioned is the method to initalise the global variables and semaphore */
/* Here we assumed variables such as active_reader_count, waiting_reader_count, active_writer_count, primary_writer_count and waiting_writer_count to be global variables */
/* This means that these variables will not be passed explicitly to the Reader Process and the Writer Process */
/* All global variables are initialised to zero */
void initialise(){
    global int active_reader_count = 0;
    global int waiting_reader_count = 0;
    global int active_writer_count = 0;
    global int primary_writer_count = 0;
    global int waiting_writer_count = 0;

    semaphore *mutex, *reader_allowed, *writer_allowed, *writer_finished;
    mutex = new semaphore;
    reader_allowed = new semaphore;
    writer_allowed = new semaphore;
    writer_finished = new semaphore;

    mutex->value = 1;
    mutex->list = NULL;

    reader_allowed->value = 0;
    reader_allowed->list = NULL;

    writer_allowed->value = 0;
    writer_allowed->list = NULL;

    writer_finished->value = 0;
    writer_finished->list = NULL;
}

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
        signal (*writer_allowed);
        primary_writer_count--;
        /* Wait until prior Writer process has finished execution of critical section */
        wait (*writer_finished);
    }

    active_writer_count = 0;

    /* If there are waiting Readers process then allow that group to access critical section and complete their execution */
    while(waitng_reader_count > 0){
        signal (*reader_allowed);
        active_reader_count++;
        waiting_reader_count--;
    } 
    signal (*mutex);
}
