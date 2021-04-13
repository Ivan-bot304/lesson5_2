public class Main {

    public static void main(String[] args) {
        long saveTime = System.currentTimeMillis();
        int step=0;
        Sklad sklad=new Sklad(20);
        Thread threadProducer=new Thread( new Producer(sklad,10));
        Thread threadConsumer=new Thread( new Consumer(sklad,11));
        while(step<30){
            long curTime = System.currentTimeMillis();
            if(curTime-saveTime>1000) {
                saveTime=curTime;
                step++;
                if(step%2>0){
                    threadConsumer.run();
                }
                else{
                    threadProducer.run();
                }
                System.out.println("Остаток = " + sklad.getCountProduct());

            }
        }



    }
}

 class  Sklad{
    public int countProduct;

    public Sklad(int countProduct) {
        this.countProduct = countProduct;
    }

    public synchronized int getCountProduct() {
        return countProduct;
    }

    public synchronized void increment(int in){
        System.out.println("+Поставка " + in);
        countProduct=countProduct+in;
    }

    public synchronized boolean decrement (int out){
        if(countProduct<out){
            return false;
        }
        else{
            System.out.println("-Покупка " + out);
             countProduct=countProduct-out;
            return true;
        }
    }
}


class Producer implements Runnable {

    Sklad sklad;
    int count;

    public Producer(Sklad sklad, int count) {
        this.sklad = sklad;
        this.count=count;
    }

    public void run() {
        sklad.increment(count);
    }
}

class Consumer implements Runnable {

    Sklad sklad;
    int count;

    public Consumer(Sklad sklad, int count) {
        this.sklad = sklad;
        this.count=count;
    }

    public void run() {

       if(!sklad.decrement(count)){
           System.out.println("НЕДОСТАТОЧНО товара");
       }
    }
}