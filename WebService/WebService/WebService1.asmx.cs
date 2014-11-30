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

            WebClient client = new WebClient();

            client.Encoding = System.Text.Encoding.UTF8;

            string html = client.DownloadString(url);

            HtmlAgilityPack.HtmlDocument dokuman = new HtmlAgilityPack.HtmlDocument();

            dokuman.LoadHtml(html);

            // Indirdigimiz html kodlarini bir HtmlDocment nesnesine yüklüyoruz.  
            HtmlNodeCollection baslik = dokuman.DocumentNode.SelectNodes("//div/h1");
            HtmlNodeCollection yer = dokuman.DocumentNode.SelectNodes("//div[@class='field-items']");
            HtmlNodeCollection duzenleyen = dokuman.DocumentNode.SelectNodes("//div[@class='field-item even']");
            HtmlNodeCollection tur = dokuman.DocumentNode.SelectNodes("//li[@class='taxonomy-term-reference-0']");
            HtmlNodeCollection date_start = dokuman.DocumentNode.SelectNodes("//span[@class='date-display-start']");
            HtmlNodeCollection date_end = dokuman.DocumentNode.SelectNodes("//span[@class='date-display-end']");
            HtmlNodeCollection date_single = dokuman.DocumentNode.SelectNodes("//span[@class='date-display-single']");

            string[] liste = new string[6];
            int i_yer = 0;
            int i_duzenleyen = 0;
            if (yer.Count == 4)
            {
                i_yer = 1;
                i_duzenleyen = 2;
            }
            else if (yer.Count == 3)
            {
                i_yer = 0;
                i_duzenleyen = 1;
            }


            string str_baslik = "";
            try
            {
                str_baslik = baslik[0].InnerText;
                str_baslik = str_baslik.Replace("&#039;", "'");
                str_baslik = str_baslik.Replace("&quot;", "\"");
            }
            catch (Exception e)
            {
                str_baslik = "";
            }

            string str_yer = "";
            try
            {
                str_yer = yer[i_yer].InnerText;
                str_yer = str_yer.Replace("&#039;", "'");
                str_yer = str_yer.Replace("&quot;", "\"");
            }
            catch (Exception e)
            {
                str_yer = "";
            }

            string str_duzenleyen = "";
            try
            {
                str_duzenleyen = duzenleyen[i_duzenleyen].InnerText;
                str_duzenleyen = str_duzenleyen.Replace("&#039;", "'");
                str_duzenleyen = str_duzenleyen.Replace("&quot;", "\"");
            }
            catch (Exception e)
            {
                str_duzenleyen = "";
            }

            string str_tur = "";
            try
            {
                str_tur = tur[0].InnerText;
                str_tur = str_tur.Replace("&#039;", "'");
                str_tur = str_tur.Replace("&quot;", "\"");

                             

            }
            catch (Exception e)
            {
                str_tur = "";
            }

            string str_date_single = "";
            try
            {
                str_date_single = date_single[0].InnerText;
                str_date_single = str_date_single.Replace("&#039;", "'");
                str_date_single = str_date_single.Replace("&quot;", "\"");
            }
            catch (Exception e)
            {
                str_date_single = "";
            }

            string str_date_start = "";
            try
            {
                str_date_start = date_start[0].InnerText;
                str_date_start = str_date_start.Replace("&#039;", "'");
                str_date_start = str_date_start.Replace("&quot;", "\"");
            }
            catch (Exception e)
            {
                str_date_start = "";
            }

            string str_date_end = "";
            try
            {
                str_date_end = date_end[0].InnerText;
                str_date_end = str_date_end.Replace("&#039;", "'");
                str_date_end = str_date_end.Replace("&quot;", "\"");
            }
            catch (Exception e)
            {
                str_date_end = "";
            }

            if (str_date_single != "")
            {
                str_date_start = str_date_single;
                str_date_end = str_date_single;
            }

            liste[0] = str_baslik;
            liste[1] = str_yer;
            liste[2] = str_duzenleyen;
            liste[3] = str_tur;
            liste[4] = str_date_start;
            liste[5] = str_date_end;

                        return liste;
        }
   


      
    }
}
