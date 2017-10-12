package challenge.stream;

/**
 * The stream reader class
 * <p>
 * This class implements a reader for an array of chars.
 * The stream can only be read once.
 */
public class Stream implements IStream {

    private char[] stream;
    private int currentPosition;

    public Stream(final String stream) {
        this.stream = stream.toCharArray();
        this.currentPosition = 0;
    }

    /**
     * This method returns the next available position in the array.
     *
     * @return the char
     */
    @Override
    public char getNext() {
        return stream[currentPosition++];
    }

    /**
     * This method returns a boolean indicating if the stream has more positions to read.
     *
     * @return the boolean
     */
    @Override
    public boolean hasNext() {
        return currentPosition < stream.length;
    }

}
