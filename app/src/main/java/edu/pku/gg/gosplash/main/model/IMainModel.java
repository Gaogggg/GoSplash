package edu.pku.gg.gosplash.main.model;

import java.util.List;

import edu.pku.gg.gosplash.common.data.Collection;

/**
 * Created by gaoge
 */
public interface IMainModel {

    void getAllCollections(int page);

    void getCuratedCollections(int page);

    void getFeaturedCollections(int page);

}
