package cn.itcast.travel.domain;

import java.util.List;

/**
 * 分页对象
 */
public class PageBean<T> {
    private int totalcount;//总记录数
    private int totalpage;//总页数
    private int currentpage;//当前页码
    private int pagesize;//每页显示的记录总数
    private List<T> list;//每页的显示数据的集合


    public int getTotalcount() {
        return totalcount;
    }

    public void setTotalcount(int totalcount) {
        this.totalcount = totalcount;
    }

    public int getTotalpage() {
        return totalpage;
    }

    public void setTotalpage(int totalpage) {
        this.totalpage = totalpage;
    }

    public int getCurrentpage() {
        return currentpage;
    }

    public void setCurrentpage(int currentpage) {
        this.currentpage = currentpage;
    }

    public int getPagesize() {
        return pagesize;
    }

    public void setPagesize(int pagesize) {
        this.pagesize = pagesize;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "PageBean{" +
                "totalcount=" + totalcount +
                ", totalpage=" + totalpage +
                ", currentpage=" + currentpage +
                ", pagesize=" + pagesize +
                ", list=" + list +
                '}';
    }
}
