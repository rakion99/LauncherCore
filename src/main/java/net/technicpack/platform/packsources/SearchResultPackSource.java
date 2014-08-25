/*
 * This file is part of Technic Launcher Core.
 * Copyright (C) 2013 Syndicate, LLC
 *
 * Technic Launcher Core is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Technic Launcher Core is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License,
 * as well as a copy of the GNU Lesser General Public License,
 * along with Technic Launcher Core.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.technicpack.platform.packsources;

import net.technicpack.launchercore.modpacks.sources.IPackSource;
import net.technicpack.platform.IPlatformApi;
import net.technicpack.platform.io.SearchResult;
import net.technicpack.platform.io.SearchResultsData;
import net.technicpack.rest.RestfulAPIException;
import net.technicpack.rest.io.PackInfo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class SearchResultPackSource implements IPackSource {
    private IPlatformApi platformApi;
    private String searchTerms;

    public SearchResultPackSource(IPlatformApi platformApi, String searchTerms) {
        this.platformApi = platformApi;
        this.searchTerms = searchTerms;
    }

    @Override
    public String getSourceName() {
        return "Modpack search results for query '"+searchTerms+"'";
    }

    @Override
    //Get PlatformPackInfo objects for every result from the given search terms.
    public Collection<PackInfo> getPublicPacks() {
        //Get results from server
        SearchResultsData results = null;
        try {
            results = platformApi.getSearchResults(searchTerms);
        } catch (RestfulAPIException ex) {
            return Collections.emptySet();
        }

        ArrayList<PackInfo> resultPacks = new ArrayList<PackInfo>(results.getResults().length);

        for(SearchResult result : results.getResults()) {
            resultPacks.add(new SearchResultPackInfo(result));
        }

        return resultPacks;
    }
}
