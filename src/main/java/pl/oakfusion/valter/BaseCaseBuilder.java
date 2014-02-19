package pl.oakfusion.valter;

import java.util.ArrayList;
import java.util.List;

public class BaseCaseBuilder {

	private ArrayList<Object[]> list = new ArrayList<>();

	public List<Object[]> toList() {
		return list;
	}

	void addToList(Object[] objects) {
		list.add(objects);
	}

}
