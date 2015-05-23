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
    public partial class Form_WellCome : Form
    {
        public Form_WellCome()
        {
            InitializeComponent();
        }

        private void btDangNhap_Click(object sender, EventArgs e)
        {
            Form_DangNhap dn = new Form_DangNhap();
            dn.Show();
        }

        private void btDangKy_Click(object sender, EventArgs e)
        {
            Form_KHDangKy dk = new Form_KHDangKy();
            dk.Show();
        }
    }
}
