using System;
using System.Data;
using System.Data.OleDb;
using System.Configuration;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Text;
using MySql.Data.MySqlClient;

public class IM_MySQLIslemleri
{
    MySqlConnection baglanti;


    public MySqlConnection IM_MySQLBaglantiGetir()
    {
        return baglanti;
    }

    public void IM_MySQLBaglatiAc()
    {
        baglanti = new MySqlConnection("");

        baglanti.Open();
    }


    public void IM_MySSQLSorguCalistir(string str_komut)
    {
        MySqlCommand komut = new MySqlCommand(str_komut, baglanti);

        komut.ExecuteNonQuery();
    }


    public void IM_MySQLTabloSil(string str_tablo_ismi)
    {
        string str_tablo_ekle = "DROP TABLE IF EXISTS " + str_tablo_ismi;

        MySqlCommand komut = new MySqlCommand(str_tablo_ekle, baglanti);

        komut.ExecuteNonQuery();
    }

    public void IM_MySQLBaglantiKapat()
    {
        baglanti.Close();

        baglanti.Dispose();
    }

    public DataSet IM_MySQLTabloGetir(string str_sorgu)
    {
        MySqlCommand komut = new MySqlCommand(str_sorgu, baglanti);

        DataSet veriler = new DataSet();

        MySqlDataAdapter adaptor = new MySqlDataAdapter(komut);

        adaptor.Fill(veriler);

        adaptor.Dispose();

        return veriler;
    }


    public DataRow IM_MySQLSatirGetir(string str_sorgu)
    {
        DataTable table = IM_MySQLTabloGetir(str_sorgu).Tables[0];

        if (table.Rows.Count == 0)
        {
            return null;
        }
        return table.Rows[0];
    }

    public MySqlDataReader IM_MySQLReaderCalistir(string str_sorgu)
    {
        MySqlCommand komut = new MySqlCommand(str_sorgu, baglanti);

        return komut.ExecuteReader();
    }

    public int IM_MySQLRakamGetir(string str_sorgu)
    {
        MySqlCommand komut = new MySqlCommand(str_sorgu, baglanti);

        return Convert.ToInt32(komut.ExecuteScalar().ToString());
    }



}