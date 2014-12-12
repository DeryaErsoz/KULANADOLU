using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Services;
using System.Net;
using System.Xml;
using System.Xml.Linq;
using HtmlAgilityPack;
using System.Text;
using MySql.Data.MySqlClient;
using System.Data;

namespace WebApplication1
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
        IM_MySQLIslemleri my_sql_islemleri = new IM_MySQLIslemleri();

        [WebMethod]
        public List<string[]> GetData()
        {
            Uri url = new Uri("https://www.anadolu.edu.tr/tr/etkinlikler-listesi");
            TurEkle(url);

            my_sql_islemleri.IM_MySQLBaglatiAc();
            my_sql_islemleri.IM_MySSQLSorguCalistir("DELETE FROM activity");
            my_sql_islemleri.IM_MySSQLSorguCalistir("ALTER TABLE activity AUTO_INCREMENT=1;");
            my_sql_islemleri.IM_MySQLBaglantiKapat();

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
                    Uri url_ = new Uri("https://www.anadolu.edu.tr" + str_link);

                    liste.Add(GetData(url_));
                }
            }
            return liste;
        }

        [WebMethod]
        public List<string[]> VerileriGonder()
        {
            my_sql_islemleri.IM_MySQLBaglatiAc();

            List<string[]> liste = new List<string[]>();

            DataTable tablo_activity = my_sql_islemleri.IM_MySQLTabloGetir("SELECT * FROM activity").Tables[0];

            for (int i = 0; i < tablo_activity.Rows.Count; i++)
            {
                string[] str_activity = new string[6];

                //name
                str_activity[0] = tablo_activity.Rows[i][1].ToString();

                //location
                str_activity[1] = tablo_activity.Rows[i][2].ToString();

                //organizer
                str_activity[2] = tablo_activity.Rows[i][3].ToString();

                //date_start
                str_activity[3] = tablo_activity.Rows[i][4].ToString();

                //date_end
                str_activity[4] = tablo_activity.Rows[i][6].ToString();

                //type_id
                DataTable tablo_type = my_sql_islemleri.IM_MySQLTabloGetir("SELECT type_name FROM type WHERE type_id=" + Convert.ToInt32(tablo_activity.Rows[i][5])).Tables[0];
                //type_name
                str_activity[5] = tablo_type.Rows[0][0].ToString();

                liste.Add(str_activity);
            }

            my_sql_islemleri.IM_MySQLBaglantiKapat();

            return liste;
        }


        public void TurEkle(Uri url)
        {
            my_sql_islemleri.IM_MySQLBaglatiAc();

            WebClient client = new WebClient();

            client.Encoding = System.Text.Encoding.UTF8;

            string html = client.DownloadString(url);

            HtmlAgilityPack.HtmlDocument dokuman = new HtmlAgilityPack.HtmlDocument();

            dokuman.LoadHtml(html);

            HtmlNodeCollection tur = dokuman.DocumentNode.SelectNodes("//select[@class='form-select']");

            string str_tur = "";

            for (int i = 0; i < tur[0].ChildNodes.Count; i++)
            {
                if (tur[0].ChildNodes[i].Name == "#text")
                {
                    str_tur = tur[0].ChildNodes[i].InnerText;

                    int i_sayi = my_sql_islemleri.IM_MySQLRakamGetir("SELECT COUNT(*) FROM type WHERE type_name='" + str_tur + "'");
                    if (i_sayi == 0)
                    {
                        //veri tabanına tür ekle
                        my_sql_islemleri.IM_MySSQLSorguCalistir("INSERT INTO type (type_name) VALUES ('" + str_tur + "')");
                    }
                }
            }
            my_sql_islemleri.IM_MySQLBaglantiKapat();

        }



        public string[] GetData(Uri url)
        {
            my_sql_islemleri.IM_MySQLBaglatiAc();

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
            HtmlNodeCollection date_time = dokuman.DocumentNode.SelectNodes("//span[@class='date-display-start']");


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
                str_baslik = str_baslik.Replace("'", "");
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
                str_yer = str_yer.Replace("'", "");
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
                str_duzenleyen = str_duzenleyen.Replace("'", "");
            }
            catch (Exception e)
            {
                str_duzenleyen = "";
            }

            string str_tur = "";
            int type_id = 1;
            try
            {
                str_tur = tur[0].InnerText;
                str_tur = str_tur.Replace("&#039;", "'");
                str_tur = str_tur.Replace("&quot;", "\"");
                type_id = my_sql_islemleri.IM_MySQLRakamGetir("SELECT type_id FROM type WHERE type_name='" + str_tur + "'");
            }
            catch (Exception e)
            {
                str_tur = "";
            }
    string str_date_single = "";
            try
            {
                str_date_single = date_single[0].Attributes["content"].Value;
                str_date_single = str_date_single.Replace("&#039;", "'");
                str_date_single = str_date_single.Replace("&quot;", "\"");
                str_date_single = str_date_single.Replace("'", "");
            }
            catch (Exception e)
            {
                str_date_single = "";
            }

            string str_date_start = "";
            try
            {
                str_date_start = date_start[0].Attributes["content"].Value;
                //str_date_start = date_start[0].InnerText;
                str_date_start = str_date_start.Replace("&#039;", "'");
                str_date_start = str_date_start.Replace("&quot;", "\"");
                str_date_start = str_date_start.Replace("'", "");
            }
            catch (Exception e)
            {
                str_date_start = "";
            }

            string str_date_end = "";
            try
            {
                str_date_end = date_end[0].Attributes["content"].Value;
                str_date_end = str_date_end.Replace("&#039;", "'");
                str_date_end = str_date_end.Replace("&quot;", "\"");
                str_date_end = str_date_end.Replace("'", "");
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
        

            my_sql_islemleri.IM_MySSQLSorguCalistir("INSERT INTO activity (name, location, organizer, date_start, type_id, date_end) VALUES ('" + str_baslik + "','" + str_yer + "','" + str_duzenleyen + "','" + str_date_start + "'," + type_id.ToString() + ",'" + str_date_end + "')");

            //my_sql_islemleri.IM_MySSQLSorguCalistir("INSERT INTO activity (name, location, organizer, date_start, type_id, date_end) VALUES ('" + str_baslik + "','" + str_yer +"','" + str_duzenleyen + "','',1,'')");

            my_sql_islemleri.IM_MySQLBaglantiKapat();
            return liste;
        }


    }
}