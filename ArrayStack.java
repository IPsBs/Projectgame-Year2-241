
public class ArrayStack implements MyStack{

    private Object[] data;
    private int t;

    private int size;



    public ArrayStack() {
        MyStack();
    }

    @Override
    public void push(Object e) throws Exception {
        if(!full()) {
            data[t+1] = e;
            insertionSort();
            size++;
            t++;
        }
        else {
            throw new Exception("List is full");
        }
    }

    @Override
    public void MyStack() {
        data = new Object[10];
        size = 0;
        t=-1;
    }

    @Override
    public Object pop() throws Exception {
        if(!empty()) {
            t=t-1;
            size = size-1;
            return data[t+1];
        }
        else{
            throw new Exception("List is empty");
        }
    }

    @Override
    public Object top() throws Exception {
        if(empty()){
            throw new Exception("List is empty");
        }
        else {
            insertionSortRe();
            return data[t];
        }
    }

    @Override
    public Boolean empty() {
        return t==-1;
    }

    @Override
    public Boolean full() {
        return t==(data.length-1);
    }



    @Override
    public void clear() {
        for(int i=0;i<t;i++) {
            data[i]=null;
        }

        t=-1;

    }
    @Override
    public int gatSize() {return size;}

    public void insertionSort() {
        Object tmp;
        int i;
        for (int j=1; j<size; j++) {
            i =j - 1;
            tmp = data[j];
            while ( (i>=0) && ((Integer)tmp > (Integer)data[i]) ) {
                data[i+1] = data[i];
                i--;
            }
            data[i+1] = tmp;

        }

    }

    public void insertionSortRe() {
        Object tmp;
        int i;
        for (int j=1; j<size; j++) {
            i =j - 1;
            tmp = data[j];
            while ( (i>=0) && ((Integer)tmp < (Integer)data[i]) ) {
                data[i+1] = data[i];
                i--;
            }
            data[i+1] = tmp;

        }

    }


}
