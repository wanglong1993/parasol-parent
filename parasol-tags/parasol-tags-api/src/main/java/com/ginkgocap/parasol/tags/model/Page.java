/**
 * 
 */
package com.ginkgocap.parasol.tags.model;

import com.fasterxml.jackson.annotation.JsonFilter;

import java.io.Serializable;
import java.util.List;

/**
 * @author gintong
 *
 */
@JsonFilter("com.ginkgocap.parasol.tags.model.Page")
public class Page<E> implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long total;
	
	private int index;
	
	private List<Tag> list;
	
	private int size;
	public Page() {}

	/**
	 * @param total
	 * @param index
	 * @param list
	 */
	public Page(Long total, int index, List<Tag> list) {
		super();
		this.total = total;
		this.index = index;
		this.list = list;
	}

	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + index;
		result = prime * result + ((list == null) ? 0 : list.hashCode());
		result = prime * result + size;
		result = prime * result + ((total == null) ? 0 : total.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Page other = (Page) obj;
		if (index != other.index)
			return false;
		if (list == null) {
			if (other.list != null)
				return false;
		} else if (!list.equals(other.list))
			return false;
		if (size != other.size)
			return false;
		if (total == null) {
			if (other.total != null)
				return false;
		} else if (!total.equals(other.total))
			return false;
		return true;
	}

	/**
	 * @param total
	 * @param index
	 * @param list
	 * @param size
	 */
	public Page(Long total, int index, List<Tag> list, int size) {
		super();
		this.total = total;
		this.index = index;
		this.list = list;
		this.size = size;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * @return the total
	 */
	public Long getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(Long total) {
		this.total = total;
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * @return the list
	 */
	public List<Tag> getList() {
		return list;
	}

	/**
	 * @param list the list to set
	 */
	public void setList(List<Tag> list) {
		this.list = list;
	}
	
	
}
