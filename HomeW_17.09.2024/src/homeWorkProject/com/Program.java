package homeWorkProject.com;

public class Program {

	public static void main(String[] args) {
		int var = 10;
		int count = 0;
		
		int[] numbers = {3, 5, 7, 2, 8, -1, 4, 10};

        int max = numbers[0];
        int min = numbers[0];
        
        int evenCount = 0;
        int oddCount = 0;
		//1
		if (var > 0) {
            System.out.println("Число положительное.");
        } else if (var < 0) {
            System.out.println("Число отрицательное.");
        } else {
            System.out.println("Число равно нулю.");
        }
		
		//2
		if(var %2 ==0 || var%5 ==0) {
			 System.out.println("FizzBuzz");
		}
		else {
			 System.out.println("Buzz");
		}
		
		//3
		if (var >= 5 && var <= 11) 
		{
            System.out.println("Утро");
        } 
		else if (var >= 12 && var <= 17) 
		{
            System.out.println("День");
        } 
		else if (var >= 18 && var <= 21)
		{
            System.out.println("Вечер");
        } 
		else
		{
            System.out.println("Ночь");
        }
		
		//4
		for (int i = 0; i<30; i++) {
			 System.out.println("Чинннннааааззззееесссс");
		}
		
		//5
		for (int i = 0; i<6; i++) {
			if(i%2==0) {
				count++;
			}
			System.out.print(i);
		}
		System.out.println("Четных числе = " + count);
		
		//6
        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] > max) {
                max = numbers[i];
            }
            if (numbers[i] < min) {
                min = numbers[i];
            }
        }

        System.out.println("Максимум: " + max);
        System.out.println("Минимум: " + min);
        
        //7
        boolean isSorted = true;

        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] < numbers[i - 1]) {
                isSorted = false;
                break;
            }
        }

        if (isSorted) {
            System.out.println("Массив отсортирован по возрастанию.");
        } else {
            System.out.println("Массив не отсортирован.");
        }
        
        //8
        for (int number : numbers) {
            if (number % 2 == 0) {
                evenCount++;
            } else {
                oddCount++;
            }
        }

        System.out.println("Четные числа: " + evenCount);
        System.out.println("Нечетные числа: " + oddCount);
        
        //9
		int n = 5;  
	    int k = 10;
	    for (int i = 1; i <= k; i++) {
           
            System.out.println(n + " x " + i + " = " + (n * i));
        }

	}
	

}
