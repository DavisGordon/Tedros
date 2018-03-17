package com.tedros.util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.LinkedList;

public final class TNetUtil {
	
	/**
     * Returns all available IP addresses.
     * <p>
     * To get the first/main local ip only you could use also
     * {@link #getLocalIP() }.
     * <p>
     * In error case or if no network connection is established, we return
     * an empty list here.
     * <p>
     * Loopback addresses are excluded - so 127.0.0.1 will not be never
     * returned.
     * <p>
     * The "primary" IP might not be the first one in the returned list.
     *
     * @return  Returns all IP addresses (can be an empty list in error case
     *          or if network connection is missing).
     * @since   0.1.0
     */
    public static Collection<InetAddress> getAllLocalIPs()
    {
        LinkedList<InetAddress> listAdr = new LinkedList<InetAddress>();
        try
        {
            Enumeration<NetworkInterface> nifs = NetworkInterface.getNetworkInterfaces();
            if (nifs == null) return listAdr;

            while (nifs.hasMoreElements())
            {
                NetworkInterface nif = nifs.nextElement();
                // We ignore subinterfaces - as not yet needed.

                Enumeration<InetAddress> adrs = nif.getInetAddresses();
                while (adrs.hasMoreElements())
                {
                    InetAddress adr = adrs.nextElement();
                    if (adr != null && !adr.isLoopbackAddress() && (nif.isPointToPoint() || !adr.isLinkLocalAddress()))
                    {
                        listAdr.add(adr);
                    }
                }
            }
            return listAdr;
        }
        catch (SocketException ex)
        {
            ex.printStackTrace();
        	//Logger.getLogger(Net.class.getName()).log(Level.WARNING, "No IP address available", ex);
            return listAdr;
        }
    }
    /**
     * Returns the current local IP address or an empty string in error case /
     * when no network connection is up.
     * <p>
     * The current machine could have more than one local IP address so might
     * prefer to use {@link #getAllLocalIPs() } or
     * {@link #getAllLocalIPs(java.lang.String) }.
     * <p>
     * If you want just one IP, this is the right method and it tries to find
     * out the most accurate (primary) IP address. It prefers addresses that
     * have a meaningful dns name set for example.
     *
     * @return  Returns the current local IP address or an empty string in error case.
     * @since   0.1.0
     */
    public static String getLocalIP()
    {

        //// This method does not work any more - I think on Windows it worked by accident
        //try
        //{
        //    String ip = InetAddress.getLocalHost().getHostAddress();
        //    return ip;
        //}
        //catch (UnknownHostException ex)
        //{
        //    Logger.getLogger(Net.class.getName()).log(Level.WARNING, null, ex);
        //    return "";
        //}
        String ipOnly = "";
        try
        {
            Enumeration<NetworkInterface> nifs = NetworkInterface.getNetworkInterfaces();
            if (nifs == null) return "";
            while (nifs.hasMoreElements())
            {
                NetworkInterface nif = nifs.nextElement();
                // We ignore subinterfaces - as not yet needed.

                if (!nif.isLoopback() && nif.isUp() && !nif.isVirtual())
                {
                    Enumeration<InetAddress> adrs = nif.getInetAddresses();
                    while (adrs.hasMoreElements())
                    {
                        InetAddress adr = adrs.nextElement();
                        if (adr != null && !adr.isLoopbackAddress() && (nif.isPointToPoint() || !adr.isLinkLocalAddress()))
                        {
                            String adrIP = adr.getHostAddress();
                            String adrName;
                            if (nif.isPointToPoint()) // Performance issues getting hostname for mobile internet sticks
                                adrName = adrIP;
                            else
                                adrName = adr.getCanonicalHostName();

                            if (!adrName.equals(adrIP))
                                return adrIP;
                            else
                                ipOnly = adrIP;
                        }
                    }
                }
            }
            if (ipOnly.length()==0)
            	System.out.println("No IP address available");
            	//Logger.getLogger(Net.class.getName()).log(Level.WARNING, "No IP address available");
            return ipOnly;
        }
        catch (SocketException ex)
        {
        	ex.printStackTrace();
            //Logger.getLogger(Net.class.getName()).log(Level.WARNING, "No IP address available", ex);
            return "";
        }
    }

}
