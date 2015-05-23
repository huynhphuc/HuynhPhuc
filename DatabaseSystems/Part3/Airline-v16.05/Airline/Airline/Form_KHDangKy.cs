using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;

namespace Airline
{
    public partial class Form_KHDangKy : Form
    {
        public Form_KHDangKy()
        {
            InitializeComponent();
        }

        private DataTable dtCB = new DataTable("Thông tin chuyến bay ứng với tuyến bay");
        private DataTable dtTBGa = new DataTable("Thông tin tuyến bay và ga");
        /**************************************************************
         * Hàm này lấy danh sách các chuyến bay ứng với tuyến bay mà
         * khách hàng vừa chọn
         * ***********************************************************/
        private void btnOK_Click(object sender, EventArgs e)
        {
            Class_Model model = new Class_Model();
            lvCBDaChon.Clear();
            lvCBDaChon.Columns.Add("Mã Số Chuyến Bay", 70, HorizontalAlignment.Center);
            lvCBDaChon.Columns.Add("Trạng Thái", 70, HorizontalAlignment.Center);
            lvCBDaChon.Columns.Add("Số Ghế Trống", 70, HorizontalAlignment.Center);
            lvCBDaChon.Columns.Add("Thời Điểm Đi", 70, HorizontalAlignment.Center);
            lvCBDaChon.Columns.Add("Thời Điểm Đến", 70, HorizontalAlignment.Center);
            lvCBDaChon.Columns.Add("Mã Số Máy Bay", 70, HorizontalAlignment.Center);
            lvCBDaChon.Columns.Add("Mã Số Tuyến Bay", 70, HorizontalAlignment.Center);
            lvCBDaChon.View = View.Details;
            lvCBDaChon.GridLines = true;
            lvCBDaChon.BackColor = Color.Aqua;
            lvCBDaChon.ForeColor = Color.Blue;
            try
            {
                dtCB.Clear();
                dtCB = model.danhSachCBMuonDi(dtCB, txtMSTB.Text);

                int i;
                for (i = 0; i < dtCB.Rows.Count; i++)
                {
                    lvCBDaChon.Items.Add(dtCB.Rows[i].ItemArray[0].ToString());
                    lvCBDaChon.Items[i].SubItems.Add(dtCB.Rows[i].ItemArray[1].ToString());
                    lvCBDaChon.Items[i].SubItems.Add(dtCB.Rows[i].ItemArray[2].ToString());
                    lvCBDaChon.Items[i].SubItems.Add(dtCB.Rows[i].ItemArray[3].ToString());
                    lvCBDaChon.Items[i].SubItems.Add(dtCB.Rows[i].ItemArray[4].ToString());
                    lvCBDaChon.Items[i].SubItems.Add(dtCB.Rows[i].ItemArray[5].ToString());
                    lvCBDaChon.Items[i].SubItems.Add(dtCB.Rows[i].ItemArray[6].ToString());
                }
            }
            catch
            {
                MessageBox.Show("KHÔNG TÌM THẤY TUYẾN BAY NÀY!!!", "ERROR", MessageBoxButtons.OK);
            }
        }

        private void btnNext_Click(object sender, EventArgs e)
        {
            Form_DangKyChuyenBay dk = new Form_DangKyChuyenBay();
            if (lvCBDaChon.SelectedItems.Count == 1)
            {
                dk.MSCB = lvCBDaChon.SelectedItems[0].SubItems[0].Text;
                dk.Show();
            }
            else
            {
                MessageBox.Show("Bạn chưa chọn chuyến bay");
            }
        }

        private void Form_DangKy_Load(object sender, EventArgs e)
        {
            Class_Model model = new Class_Model();

            lvTBGa.Columns.Add("Mã Số Tuyến Bay", 70, HorizontalAlignment.Center);
            lvTBGa.Columns.Add("Ga Đi", 70, HorizontalAlignment.Center);
            lvTBGa.Columns.Add("Ga Đến", 70, HorizontalAlignment.Center);
            lvTBGa.View = View.Details;
            lvTBGa.GridLines = true;
            lvTBGa.BackColor = Color.Aqua;
            lvTBGa.ForeColor = Color.Blue;

            lvCBDaChon.MultiSelect = false;
            lvCBDaChon.CheckBoxes = false;
            // Select the item and subitems when selection is made.
            lvCBDaChon.FullRowSelect = true;

            dtTBGa = model.thongTinTBGa(dtTBGa);

            int i;
            for (i = 0; i < dtTBGa.Rows.Count; i++)
            {
                lvTBGa.Items.Add(dtTBGa.Rows[i].ItemArray[0].ToString());
                lvTBGa.Items[i].SubItems.Add(dtTBGa.Rows[i].ItemArray[1].ToString());
                lvTBGa.Items[i].SubItems.Add(dtTBGa.Rows[i].ItemArray[2].ToString());
            }
        }
    }
}
