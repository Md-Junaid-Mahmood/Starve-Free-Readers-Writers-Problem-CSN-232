public class Process{
    public static void main(String[] args) {
        TemplateOfProcess tmp = new TemplateOfProcess();

        ReaderProcess rd1 = new ReaderProcess("Reader1");
        ReaderProcess rd2 = new ReaderProcess("Reader2");
        WriterProcess wr1 = new WriterProcess("Writer1");
        WriterProcess wr2 = new WriterProcess("Writer2");
        ReaderProcess rd3 = new ReaderProcess("Reader3");
        ReaderProcess rd4 = new ReaderProcess("Reader4");
        WriterProcess wr3 = new WriterProcess("Writer3");
        ReaderProcess rd5 = new ReaderProcess("Reader5");
        WriterProcess wr4 = new WriterProcess("Writer4");
        ReaderProcess rd6 = new ReaderProcess("Reader6");

        rd1.start();
        rd2.start();
        wr1.start();
        wr2.start();
        rd3.start();
        rd4.start();
        wr3.start();
        rd5.start();
        wr4.start();
        rd6.start();
    }
}
