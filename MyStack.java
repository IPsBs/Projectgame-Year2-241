public interface MyStack {

    public void MyStack();

    public  void push(Object e) throws Exception;

    public Object pop() throws  Exception;

    public Object top() throws Exception;

    public Boolean empty();

    public Boolean full();

    public void clear();

    public int gatSize();
}
