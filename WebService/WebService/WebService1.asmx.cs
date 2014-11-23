using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using System.Net;
using System.Xml;
using System.Xml.Linq;
using System.Net;
using HtmlAgilityPack;

namespace WebService
{
    /// <summary>
    /// Summary description for WebService1
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    // [System.Web.Script.Services.ScriptService]
    public class WebService1 : System.Web.Services.WebService
    {

        [WebMethod]
        public List<string> GetData()
        {
           
            Uri url = new Uri("https://www.anadolu.edu.tr/tr/etkinlikler-listesi");
            WebClient client = new WebClient();
            client.Encoding = System.Text.Encoding.UTF8;
            string html = client.DownloadString(url);
            

            HtmlAgilityPack.HtmlDocument dokuman = new HtmlAgilityPack.HtmlDocument();
            dokuman.LoadHtml(html);
        
            HtmlNodeCollection basliklar = dokuman.DocumentNode.SelectNodes("//div/h3");
            HtmlNodeCollection icerikler = dokuman.DocumentNode.SelectNodes("//div[@class='views-field views-field-php']");
            HtmlNodeCollection tarihler = dokuman.DocumentNode.SelectNodes("//div[@class='field-content']");
     
            List<string> liste = new List<string>();
         

            foreach (var baslik in basliklar)
            {
                liste.Add(baslik.InnerText);
                
            }
            foreach (var icerik in icerikler)
            {
                liste.Add(icerik.InnerText);
                
            }
            foreach (var tarih in tarihler)
            {
                liste.Add(tarih.InnerText);
                
            }
 
            return liste;
        }
   


      
    }
}
