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
        public List<string[]> GetDatas()
        {
            List<string[]> liste = new List<string[]>();
            HtmlWeb hw = new HtmlWeb();
            HtmlAgilityPack.HtmlDocument doc = hw.Load("https://www.anadolu.edu.tr/tr/etkinlikler-listesi");
            HtmlNodeCollection linkNodes = doc.DocumentNode.SelectNodes("//a[@href]");
            foreach (HtmlNode linkNode in linkNodes)
            {
                HtmlAttribute link = linkNode.Attributes["href"];

                string str_link = link.Value;



                if (str_link.Contains("/?q=node"))
                {
                    Uri url = new Uri("https://www.anadolu.edu.tr" + str_link);

                    liste.Add(GetData(url));
                }
            }

            return liste;
        }


        [WebMethod]
        public string[] GetData(Uri url)
        {
           
            Uri url = new Uri("https://www.anadolu.edu.tr/tr/etkinlikler-listesi");
            WebClient client = new WebClient();
            client.Encoding = System.Text.Encoding.UTF8;
            string html = client.DownloadString(url);
            

            HtmlAgilityPack.HtmlDocument dokuman = new HtmlAgilityPack.HtmlDocument();
            dokuman.LoadHtml(html);

            HtmlNodeCollection baslik = dokuman.DocumentNode.SelectNodes("//div/h1");
            HtmlNodeCollection yer = dokuman.DocumentNode.SelectNodes("//div[@class='field-items']");
            HtmlNodeCollection duzenleyen = dokuman.DocumentNode.SelectNodes("//div[@class='field-item even']");
            HtmlNodeCollection tur = dokuman.DocumentNode.SelectNodes("//li[@class='taxonomy-term-reference-0']");
            HtmlNodeCollection date_start = dokuman.DocumentNode.SelectNodes("//span[@class='date-display-start']");
            HtmlNodeCollection date_end = dokuman.DocumentNode.SelectNodes("//span[@class='date-display-end']");
            HtmlNodeCollection date_single = dokuman.DocumentNode.SelectNodes("//span[@class='date-display-single']");
     
            List<string> liste = new List<string>();
         

            
 
            return liste;
        }
   


      
    }
}
