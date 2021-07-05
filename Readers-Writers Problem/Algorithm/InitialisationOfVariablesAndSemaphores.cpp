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
    int active_reader_count = 0;
    int waiting_reader_count = 0;
    int active_writer_count = 0;
    int primary_writer_count = 0;
    int waiting_writer_count = 0;

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