## <div align = "center"> Starve-Free Readers Writers Problem (CSN-232) </div>
One of the common strategies that is used for avoiding starvation in any scheduling algorithms is to give a higher priority to a job coming first. In other words, this means that a strategy like that of a *First Come First Serve (FCFS)* is followed.

Similarly, for providing a starvation free solution to Readers-Writers problem, approach that was followed is like that of a *First Come First Serve (FCFS)* strategy. In the given Readers-Writers problem, it is considered that multiple Reader processes can access the critical section simultaneously. However, only one Writer process can be allowed to access the critical section at a time. In addition, a Reader process and a Writer process cannot access the critical section simultaneously.

---

Now the logic behind the algorithm provided for starvation free solution to Readers-Writers problem is given below. Here, we have assumed two scenarios. In first case, initially a Reader process is inside critical section. In second case, initially a Writer process is inside critical section. 

  1. **Let us assume that initially Readers are executing the critical section.**
     + Now, if another Reader arrives, then the algorithm gives it the permission to access the critical section as multiple Readers are allowed inside critical section. 
     + However, if a Writer arrives then, the algorithm will ask him to wait until all currently active Readers (which are present inside critical section) complete their execution.
     + Now, there is Writer that is wating for entering the critical section. Thus, the algorithm will not give permission to anymore Reader (that comes after that Writer) to access the critical section.
     + Once, all the active Readers complete execution, then all waiting Writers (until that time) are given opportunity to access critical section one at a time.
     + Thus, above logic eliminates the possibility of the starvation of any Writer. 
  
  2. **Now, let us assume that initially a Writer is executing the critical section.**
     + Now, if another Writer approaches, then it must wait as algorithm gives permission to only one Writer to access the critical section at a time.
     + In this way, if continuously n Writers request for access to the critical section, then their request will be granted one at a time, but not necessarily in the same order.
     + However, if a Reader arrives then, the algorithm will ask him to wait until all Writers (which are waiting before that Reader for access to the critical section) complete their execution.
     + Now, there is a Reader that is waiting for entering the critical section. Thus, the algorithm will not give permission to anymore Writer (that comes after that Reader) to access the critical section.
     + Once, all the Writers that were waiting before that Reader complete execution, then all waiting Readers (until that time) are given opportunity to access critical section simultaneously.
     + Thus, above logic eliminates the possibility of the starvation of any Reader. 
   
  3. Thus, Readers and Writers are allowed to access to the critical section in alternate turns with the constraint that multiple Readers can access the critical section simultaneously, but only one Writer can access the critical section at a time. In addition, though the above-mentioned algorithm does not rigidly follow First Come First Serve (FCFS) approach, but it is based on that strategy only. 

---

**Description of the semaphore used in the structure of a Reader process and a Writer process is given below:** 
  + The `mutex` semaphore provides mutual exclusion among Readers and Writers. It is initialised to 1.
  + The `reader_allowed` semaphore makes sure that a given Reader process has necessary permission to access the critical section. It is initialised to 0.
  + The `writer_allowed` semaphore makes sure that a given Writer process has necessary permission to access the critical section. It is initialised to 0.
  + The `writer_finished` semaphore makes sure that only one Writer process is inside critical section, even though multiple Writer processes have permission to access it. It is initialised to 0. 

**Description of the variables used in the structure of a Reader process and a Writer process is given below:**
  + The `active_reader_count` variable stores the number of Reader processes that are currently inside the critical section. It is initialised to 0.
  + The `waiting_reader_count` variable stores the number of Reader processes that are waiting for the access to the critical section. They will gain access only when the group of Writer processes that came before it completes their execution. It is initialised to 0.
  + The `active_writer_count` variable is used for checking whether any Writer process is inside critical section or not. If a Writer process is inside critical section, then it is set to 1. Otherwise, it is 0.
  + The `primary_writer_count` variable stores the number of waiting Writer processes that must be given access to the critical section before any Reader process can access the critical section. It is initialised to 0.
  + The `waiting_writer_count` variable stores the number of Writer processes that are waiting for the access to the critical section. They will gain access only when the group of Reader processes that came before it completes their execution. It is initialised to 0. 
