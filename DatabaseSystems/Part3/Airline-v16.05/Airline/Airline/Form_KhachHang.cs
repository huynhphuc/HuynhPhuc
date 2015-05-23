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
    public partial class Form_KhachHang : Form
    {
        private DataTable dtKH = new DataTable("Thông tin khách hàng");
        public Form_KhachHang()
        {
            InitializeComponent();
        }
        
        private void btnOK_Click(object sender, EventArgs e)
        {
            Class_Model model = new Class_Model();
            lvUpdateKH.Clear();
           try
           {
               string remain = txtMSKH.Text.Remove(2, 10);  // Chỉ lấy "NL" hay "TE" trong MSKH
               if (remain == "NL") // load dữ liệu tùy thuộc NL hay TE vào dataTable
               {
                   lvUpdateKH.Clear();
                   lvUpdateKH.Columns.Add("Mã Số Của Quý Khách", 70, HorizontalAlignment.Center);
                   lvUpdateKH.Columns.Add("Họ Tên", 70, HorizontalAlignment.Center);
                   lvUpdateKH.Columns.Add("Ngày Sinh", 70, HorizontalAlignment.Center);
                   lvUpdateKH.Columns.Add("Giới Tính", 70, HorizontalAlignment.Center);
                   lvUpdateKH.Columns.Add("Quốc Tịch", 70, HorizontalAlignment.Center);
                   lvUpdateKH.Columns.Add("Số Điện Thoại", 70, HorizontalAlignment.Center);
                   lvUpdateKH.Columns.Add("Địa Chỉ", 70, HorizontalAlignment.Center);
                   lvUpdateKH.Columns.Add("Mã Số Phí Hàng Hóa", 70, HorizontalAlignment.Center);
                   lvUpdateKH.Columns.Add("Mã Số Chuyến Bay", 70, HorizontalAlignment.Center);
                   lvUpdateKH.Columns.Add("Khối Lượng Vượt", 70, HorizontalAlignment.Center);
                   lvUpdateKH.Columns.Add("CMND", 70, HorizontalAlignment.Center);
                   lvUpdateKH.Columns.Add("Passport", 70, HorizontalAlignment.Center);

                   lvUpdateKH.View = View.Details;
                   lvUpdateKH.GridLines = true;
                   lvUpdateKH.BackColor = Color.Aqua;
                   lvUpdateKH.ForeColor = Color.Blue;

                   dtKH.Clear();
                   dtKH = model.thongTinKHNL(dtKH, txtMSKH.Text);

                   int i;
                   for (i = 0; i < dtKH.Rows.Count; i++)
                   {
                       lvUpdateKH.Items.Add(dtKH.Rows[i].ItemArray[0].ToString());// do có 12 cột
                       for (int j = 1; j < 12; j++)
                           lvUpdateKH.Items[i].SubItems.Add(dtKH.Rows[i].ItemArray[j].ToString());
                   }
               }
               else
               {
                   lvUpdateKH.Clear();
                   lvUpdateKH.Columns.Add("Mã Số Của Quý Khách", 70, HorizontalAlignment.Center);
                   lvUpdateKH.Columns.Add("Họ Tên", 70, HorizontalAlignment.Center);
                   lvUpdateKH.Columns.Add("Ngày Sinh", 70, HorizontalAlignment.Center);
                   lvUpdateKH.Columns.Add("Giới Tính", 70, HorizontalAlignment.Center);
                   lvUpdateKH.Columns.Add("Quốc Tịch", 70, HorizontalAlignment.Center);
                   lvUpdateKH.Columns.Add("Số Điện Thoại", 70, HorizontalAlignment.Center);
                   lvUpdateKH.Columns.Add("Địa Chỉ", 70, HorizontalAlignment.Center);
                   lvUpdateKH.Columns.Add("Mã Số Phí Hàng Hóa", 70, HorizontalAlignment.Center);
                   lvUpdateKH.Columns.Add("Mã Số Chuyến Bay", 70, HorizontalAlignment.Center);
                   lvUpdateKH.Columns.Add("Khối Lượng Vượt", 70, HorizontalAlignment.Center);
                   lvUpdateKH.Columns.Add("Thông Tin Khai Sinh", 70, HorizontalAlignment.Center);
                   lvUpdateKH.Columns.Add("Mã Số Người Giám Hộ", 70, HorizontalAlignment.Center);
                   lvUpdateKH.View = View.Details;
                   lvUpdateKH.GridLines = true;
                   lvUpdateKH.BackColor = Color.Aqua;
                   lvUpdateKH.ForeColor = Color.Blue;

                   dtKH.Clear();
                   dtKH = model.thongTinKHTE(dtKH, txtMSKH.Text);

                   int i;
                   for (i = 0; i < dtKH.Rows.Count; i++)
                   {
                       lvUpdateKH.Items.Add(dtKH.Rows[i].ItemArray[0].ToString());// do có 12 cột
                       for (int j = 1; j < 12; j++)
                           lvUpdateKH.Items[i].SubItems.Add(dtKH.Rows[i].ItemArray[j].ToString());
                   }
               }
           }
           catch
           {
               MessageBox.Show("KHÔNG TÌM THẤY MÃ SỐ KHÁCH HÀNG NÀY!!!", "ERROR", MessageBoxButtons.OK);
           }
        }

        private void btnSua_Click(object sender, EventArgs e)
        {
            if (txtCMND.Text.Length == 9 || txtCMND.Text.Length == 8)
                lblCMND.ForeColor = Color.Black;
            else
            {
                lblCMND.ForeColor = Color.Red;
                return;
            }
            if (txtCMND.Text.Length == 9)
            {
                try
                {
                    Convert.ToDecimal(txtCMND.Text);
                }
                catch
                {
                    lblCMND.ForeColor = Color.Red;
                    return;
                }
            }

            if (txtGioiTinh.Text == "Nam" || txtGioiTinh.Text == "Nữ" || txtGioiTinh.Text == "Nu")
            {
                lblGioiTinh.ForeColor = Color.Black;
            }
            else
            {
                lblGioiTinh.ForeColor = Color.Red;
                return;
            }
            if (txtPassport.Text.Length != 8)
            {
                lblPassport.ForeColor = Color.Red;
                return;
            }
            else
            {
                lblPassport.ForeColor = Color.Black;
                if (txtPassport.Text == "Không có")
                {
                    lblPassport.ForeColor = Color.Black;
                }
                else
                {
                    try
                    {
                        lblPassport.ForeColor = Color.Black;
                        Convert.ToDecimal(txtPassport.Text.Remove(0, 1));
                    }
                    catch
                    {
                        lblPassport.ForeColor = Color.Red;
                        return;
                    }
                }
            }
            if (txtPassport.Text != "Không có")
            {
                string checkPassPort = txtPassport.Text.Remove(1, 7);
                if (checkPassPort == "1" || checkPassPort == "2" || checkPassPort == "3" || checkPassPort == "4"
                    || checkPassPort == "5" || checkPassPort == "6" || checkPassPort == "7" ||
                    checkPassPort == "8" || checkPassPort == "9")
                {
                    lblPassport.ForeColor = Color.Red;
                    return;
                }
                else lblPassport.ForeColor = Color.Black;
            }

            if (txtSoDT.Text.Length == 10 || txtSoDT.Text.Length == 11)
            {
                lblSoDT.ForeColor = Color.Black;
            }
            else
            {
                lblSoDT.ForeColor = Color.Red;
                return;
            }

            Class_Model model = new Class_Model();
            try
            {
                model.updateKhachHang(txtMSKH.Text, txtHoTen.Text, txtNgaySinh.Text,
                txtGioiTinh.Text, txtQuocTich.Text, txtSoDT.Text, txtDiaChi.Text,
                txtMSCB.Text, Convert.ToDouble(txtKLVuot.Text),
                txtCMND.Text, txtPassport.Text, txtThongTinKS.Text, txtMSNGH.Text);
            }
            catch 
            {
                MessageBox.Show("THÔNG TIN UPDATE KHÔNG HỢP LỆ!!!", "ERROR", MessageBoxButtons.OK);
            }
        }

        private void btnHuy_Click(object sender, EventArgs e)
        {
            if (MessageBox.Show("Quý khách có muốn thoát không?", "THOÁT", 
                MessageBoxButtons.YesNo, MessageBoxIcon.Question) == DialogResult.Yes)
            {
                Close();
                System.Windows.Forms.Application.Exit();
            }
        }

        private void Form_KhachHang_Load(object sender, EventArgs e)
        {
            gbUpdate.Hide(); // tạm ẩn groupBox cập nhật thông tin
        }

        private void btnDongY_Click(object sender, EventArgs e)
        {
            gbUpdate.Show();
            txtHoTen.Clear();
            txtNgaySinh.Clear(); txtGioiTinh.Clear();
            txtQuocTich.Clear(); txtSoDT.Clear(); txtDiaChi.Clear();
            txtMSCB.Clear(); txtKLVuot.Clear(); txtCMND.Clear(); txtPassport.Clear();
            txtThongTinKS.Clear(); txtMSNGH.Clear();
            Class_Model model = new Class_Model();
            model.showThongTinKHInTextBox(txtMSKH.Text, txtHoTen, txtNgaySinh, txtGioiTinh,
                txtQuocTich, txtSoDT, txtDiaChi, txtMSCB, txtKLVuot, txtCMND, txtPassport,
                txtThongTinKS, txtMSNGH);
        }
    }
}
