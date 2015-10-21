package median;

import javax.swing.JOptionPane;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	static Scanner scanner;
	static ArrayList<Integer> array1;
	static ArrayList<Integer> array2;
	static Integer s1, s2, e1, e2, size;
	
    public static ArrayList<Integer> divideStringLineToArray(String s) {
    	ArrayList<Integer> tmp = new ArrayList<Integer>();
        int index = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == ' ') {
                if (i > index) {
                    tmp.add(Integer.parseInt(s.substring(index, i)));
                }
                index = i + 1;
            }
        }
        if (index < s.length()) {
            tmp.add(Integer.parseInt(s.substring(index)));
        }
        return tmp;
    }
	
	private static void readDataFromFile() {
		try{
			scanner = new Scanner(new File("res//input.txt"));
			if(scanner.hasNext()){
				array1 = divideStringLineToArray(scanner.nextLine());
			}else{
				JOptionPane.showMessageDialog(null, "Первый массив не найден!");
			}
			if(scanner.hasNext()){
				array2 = divideStringLineToArray(scanner.nextLine());
			}else{
				JOptionPane.showMessageDialog(null, "Второй массив не найден!");
			}
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Файл не найден!");
		}
	}
	
	private static void cutArrays(Integer c, Integer i1, Integer i2, boolean is_first_array) {
		s1 = Math.max(s1, Math.min(i1 + (is_first_array ? 1 : 0), i1 - (c - size + 1)));
		e1 = Math.min(e1, Math.max(i1, i1 - (c - size) + (is_first_array ? 1 : 0)));
		s2 = Math.max(s2, Math.min(i2 + (is_first_array ? 0 : 1), i2 - (c - size + 1)));
		e2 = Math.min(e2, Math.max(i2, i2 - (c - size) + (is_first_array ? 0 : 1)));
	}
	
	private static Integer searchIndex(ArrayList<Integer> array, Integer s, Integer e, Integer v) {			
		Integer tmp_s = s, tmp_e = e, tmp_i = s;
		while(tmp_e - tmp_s > 1){
			tmp_i = (tmp_e + tmp_s) / 2;
			if (v < array.get(tmp_i)){
				tmp_e = tmp_i;
			}else{
				tmp_s = tmp_i;
			}
		}
		return v < array.get(tmp_s) ? tmp_s : tmp_e;
	}
	
	public static void main(String args[]){
		readDataFromFile();
		if (array1.size() != array2.size() || array1.size() == 0) return;
		//поиск медианы (ищем медианные элементы)
	
		size = array1.size();
		s1 = 0;
		e1 = size;
		s2 = 0;
		e2 = size; //индексы - диапазоны поиска: s - начальный индекс, e - конечный.
		
		boolean f = false;
		while((e1 - s1) + (e2 - s2) > 2){ //отсекаем лишние элементы до тех пор пока в двух массивах их не осталось 2
			Integer i1, i2, c;
			f = !f;
			if (f){//ищем в массивах поочередно true - в первом, false - во втором
				if (e1 - s1 <= 0) continue; 
				i1 = (e1 + s1) / 2;
				i2 = searchIndex(array2, s2, e2, array1.get(i1));
			}else{
				if (e2 - s2 <= 0) continue; 
				i2 = (e2 + s2) / 2;
				i1 = searchIndex(array1, s1, e1, array2.get(i2));
			}
			c = i1 + i2; //индекс числа v1 в общем массиве	
			cutArrays(c, i1, i2, f);//меняем диапазоны поиска
		}
		//оба элемента найдены, считаем медиану
		Double s = 0.0;
		while (e1 - s1 > 0){ s += array1.get(e1 - 1); e1--; }
		while (e2 - s2 > 0){ s += array2.get(e2 - 1); e2--; }
		System.out.println("Median is " + s / 2);
	}
}