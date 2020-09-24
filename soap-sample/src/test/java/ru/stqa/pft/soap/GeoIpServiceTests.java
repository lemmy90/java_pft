package ru.stqa.pft.soap;

import com.lavasoft.GeoIPService;
import com.lavasoft.GetIpLocationResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class GeoIpServiceTests {

    @Test
    public void testMyIp(){

        String geoIp = new GeoIPService().getGeoIPServiceSoap12().getIpLocation("91.221.102.34");
        Assert.assertEquals(geoIp, "<GeoIP><Country>RU</Country><State>66</State></GeoIP>");
    }

    @Test
    public void testInvalidIp(){

        String geoIp = new GeoIPService().getGeoIPServiceSoap12().getIpLocation("91.221.102.xxx");
        Assert.assertEquals(geoIp, "<GeoIP><Country>RU</Country><State>66</State></GeoIP>");
    }
}
