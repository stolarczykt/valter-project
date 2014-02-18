package pl.oakfusion.valter;

import java.util.ArrayList;
import java.util.List;

public class BaseCaseBuilder {

	private ArrayList<Object[]> list = new ArrayList<Object[]>();

	public List<Object[]> toList() {
		return list;
	}

	public void addToList(Object[] objects) {
		list.add(objects);
	}

}
