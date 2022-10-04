package com.laioffer1.jupiter.service;

import org.springframework.stereotype.Service;
import com.laioffer1.jupiter.dao.FavoriteDao;
import com.laioffer1.jupiter.entity.db.Item;
import com.laioffer1.jupiter.entity.db.ItemType;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

@Service
public class FavoriteService {

    @Autowired
    private FavoriteDao favoriteDao;

    public void setFavoriteItem(String userId, Item item) {
        favoriteDao.setFavoriteItem(userId, item);
    }

    public void unsetFavoriteItem(String userId, String itemId) {
        favoriteDao.unsetFavoriteItem(userId, itemId);
    }

    public Map<String, List<Item>> getFavoriteItems(String userId) {
        Map<String, List<Item>> itemMap = new HashMap<>();
        for (ItemType type : ItemType.values()) {
            itemMap.put(type.toString(), new ArrayList<>());
        }
        Set<Item> favorites = favoriteDao.getFavoriteItems(userId);
        for(Item item : favorites) {
            itemMap.get(item.getItemType().toString()).add(item);
        }
        return itemMap;
    }
}

