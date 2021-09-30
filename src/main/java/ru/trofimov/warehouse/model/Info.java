package ru.trofimov.warehouse.model;

import java.util.List;

public class Info<T> {

    private final long totalItems;

    private final long offset;

    private final List<T> items;

    private final String previousPage;

    private final String nextPage;


    public Info(long offset, long limit, List<T> list, long fullSize, String url) {
        this.offset = offset;
        this.items = list;
        this.totalItems = list.size();

        String parameters = parameters(limit, fullSize, false);
        previousPage = parameters != null ? url + parameters : null;
        parameters = parameters(limit, fullSize, true);
        nextPage = parameters != null ? url + parameters : null;
    }

    public long getOffset() {
        return offset;
    }

    public long getTotalItems() {
        return totalItems;
    }

    public List<T> getItems() {
        return items;
    }

    public String getPreviousPage() {
        return previousPage;
    }

    public String getNextPage() {
        return nextPage;
    }

    private String parameters(long limit, long fullSize, boolean isNext){
        StringBuilder builder = new StringBuilder();
        String localOffset = "";
        if (offset - totalItems > 0 && !isNext){
            localOffset = "offset=" + (offset - totalItems);
        }

        if (isNext){
            if (offset + totalItems < fullSize){
                localOffset = "offset=" + (offset + totalItems);
            }else {
                return null;
            }
        }

        if (localOffset.length() > 0){
            builder.append(localOffset).append("&");
        }

        if (offset >= 0 && limit != Long.MAX_VALUE){
            builder.append("limit=").append(limit);
        }

        return builder.length() > 0 ? "?" + builder.toString() : "";
    }
}
