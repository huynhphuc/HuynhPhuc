using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using Oracle.DataAccess.Types;
using Oracle.DataAccess.Client;

namespace Airline
{
    public partial class Form_DangNhap : Form
    {
        public Form_DangNhap()
        {
            InitializeComponent();
        }

        private void btDangNhap_Click(object sender, EventArgs e)
        {
            Class_Model model = new Class_Model();
            OracleConnection cn = model.connect(txtUser.Text, txtPassword.Text);
            try
            {
                cn.Open();
                if (txtUser.Text == "NhanVien")
                {
                    MessageBox.Show("Nhân viên đăng nhập thành công", "THÀNH CÔNG", MessageBoxButtons.OK);
                    Form_NhanVien nv = new Form_NhanVien();
                    nv.username = txtUser.Text;
                    nv.password = txtPassword.Text;
                    nv.Show();
                    this.Close();
                }
                else if (txtUser.Text == "KhachHang")
                {
                    MessageBox.Show("Khách hàng đăng nhập thành công", "THÀNH CÔNG", MessageBoxButtons.OK);
                    Form_KhachHang kh = new Form_KhachHang();
                    //kh.username = txtUser.Text;
                    //kh.password = txtPassword.Text;
                    kh.Show();
                    this.Close();
                }
                cn.Close();

            }
            catch
            {
                MessageBox.Show("Bạn đăng nhập sai thông tin", "ERROR", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
             
        }

        private void btThoat_Click(object sender, EventArgs e)
        {
            if (MessageBox.Show("Bạn muốn thoát", "THÔNG BÁO", MessageBoxButtons.YesNo, MessageBoxIcon.Question) == DialogResult.Yes)
                Close();
        }
    }
}
