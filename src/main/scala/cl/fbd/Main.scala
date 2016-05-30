package cl.fbd

import scalanative.native._
import scalanative.runtime.GC
import scalanative.libc.stdlib._

/*
 *
 */

object Main {
  def main (args : Array [String]) = {
    testBoehm ()
    
    ()
  }
  
  /*
   * 
   */
   
  def testBoehm () : Unit = {
    fprintf(stdout, c"Test Boehm\n")
    
    // should be small values
    val heapSizeBefore = GC2.GC_get_heap_size ()
    val freeBytesBefore = GC2.GC_get_free_bytes ()
          
    fprintf(stdout, c"testBoehm (): before useMem\n")
    
    fprintf(stdout, c"heap size: %d\n", heapSizeBefore)
    fprintf(stdout, c"free bytes: %d\n", freeBytesBefore)
    fprintf(stdout, c"\n")
    
    useMem ()

    GC2.GC_gcollect ()
    
    val heapSizeAfter1 = GC2.GC_get_heap_size ()
    val freeBytesAfter1 = GC2.GC_get_free_bytes()
          
    fprintf(stdout, c"testBoehm (): after useMem and after gc\n")
    
    fprintf(stdout, c"heap size: %d\n", heapSizeAfter1)
    fprintf(stdout, c"free bytes: %d\n", freeBytesAfter1)
    fprintf(stdout, c"\n")

    ()
  }
  
  /*
   * Allocates memory. 
   * 
   * The memory is unreachable after this function return
   */
   
  def useMem () : Unit = {
    import scalanative.runtime
    
    val ptr = GC.malloc (10000000).cast[Ptr[Byte]]
    
    fprintf(stdout, c"useMem (): before gc\n")
    
    fprintf(stdout, c"ptr address hex: %p, int: %d\n", ptr, ptr)
    
    val heapSizeBefore = GC2.GC_get_heap_size ()
    val freeBytesBefore = GC2.GC_get_free_bytes()
          
    fprintf(stdout, c"heap size: %d\n", heapSizeBefore)
    fprintf(stdout, c"free bytes: %d\n", freeBytesBefore)
    fprintf(stdout, c"\n")
        
    // the array is in used inside "useMem", so Boehm GC cannot free the memory    
    GC2.GC_gcollect ()
    
    val heapSizeAfter = GC2.GC_get_heap_size ()
    val freeBytesAfter = GC2.GC_get_free_bytes()
          
    fprintf(stdout, c"useMem (): after gc\n")
    
    fprintf(stdout, c"heap size: %d\n", heapSizeAfter)
    fprintf(stdout, c"free bytes: %d\n", freeBytesAfter)
    fprintf(stdout, c"\n")
    
    // do something with the memory (force the use of the array)
    !ptr = 'H'
    
    ()    
  }
}

// some external C library used in this test

// gc.h
@link("gc")
@extern 
object GC2 {
  @name("GC_gcollect")
  def GC_gcollect () : Unit = extern
  
  @name("GC_get_heap_size")
  def GC_get_heap_size(): CSize = extern
  
  @name("GC_get_free_bytes")
  def GC_get_free_bytes(): CSize = extern
}

