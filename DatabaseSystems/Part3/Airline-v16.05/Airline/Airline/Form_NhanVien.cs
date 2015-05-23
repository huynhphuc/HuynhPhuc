using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using Oracle.DataAccess.Client;
using Oracle.DataAccess.Types;

namespace Airline
{
    public partial class Form_NhanVien : Form
    {
        public string username;
        public string password;
        public Form_NhanVien()
        {
            InitializeComponent();
        }
       

        private void btChiTietKhachHang_Click(object sender, EventArgs e)
        {
            Class_Model model = new Class_Model();
            //Sets the data source that the DataGridView is displaying data for.
            dtInfo.DataSource = model.KhachHang_ChuyenBay(username, password);
        }

        private void btnCB_Click(object sender, EventArgs e)
        {
            Class_Model model = new Class_Model();
            model.danhSachCB(dtInfo);
        }

        private void btnKHCB_Click(object sender, EventArgs e)
        {
            Class_Model model = new Class_Model();
            model.khachHangDKChuyenBay(dtInfo);
        }

    }
}
