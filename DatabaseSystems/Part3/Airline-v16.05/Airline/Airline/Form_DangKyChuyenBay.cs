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
    public partial class Form_DangKyChuyenBay : Form
    {
        public string MSCB;

        public Form_DangKyChuyenBay()
        {
            InitializeComponent();
        }
        /************************************************************
         * Hàm này thông báo khách hàng biết thông tin đăng ký sai ở
         * đâu, nếu đúng gọi hàm insertKhachHang trong lớp Model
         * để insert khách hàng tương ứng với người lớn hay trẻ em.
         * *********************************************************/
        private void btnOK_Click(object sender, EventArgs e)
        {
            // Thông báo biết khách hàng nhập thông tin sai ở đâu
            if (txtNgaySinh.Text.Length != 10)
            {
                lblNgaySinh.ForeColor = Color.Red;
                return;
            }
            else lblNgaySinh.ForeColor = Color.Black;
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
                lblPassPort.ForeColor = Color.Red;
                return;
            }
            else
            {
                lblPassPort.ForeColor = Color.Black;
                if (txtPassport.Text == "Không có")
                {
                    lblPassPort.ForeColor = Color.Black;
                }
                else
                {
                    try
                    {
                        lblPassPort.ForeColor = Color.Black;
                        Convert.ToDecimal(txtPassport.Text.Remove(0, 1));
                    }
                    catch
                    {
                        lblPassPort.ForeColor = Color.Red;
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
                    lblPassPort.ForeColor = Color.Red;
                    return;
                }
                else lblPassPort.ForeColor = Color.Black;
            }

            if (txtDienThoai.Text.Length == 10 || txtDienThoai.Text.Length == 11)
            {
                lblSoDienThoai.ForeColor = Color.Black;
            }
            else
            {
                lblSoDienThoai.ForeColor = Color.Red;
                return;
            }


            // Xử lý định dạng dữ liệu nhập vào
            string dThoai = "+84";
            dThoai += txtDienThoai.Text.Remove(0, 2);

            string ngaySinh = txtNgaySinh.Text.Remove(2, 8);
            string thangSinh = txtNgaySinh.Text.Remove(0, 3);
            thangSinh = thangSinh.Remove(2, 5);
            string namSinh = txtNgaySinh.Text.Remove(0, 6);
            try// kiểm tra xem đúng định dạng ngày sinh chưa?
            {
                Decimal ngay = Convert.ToDecimal(ngaySinh);
                Decimal thang = Convert.ToDecimal(thangSinh);
                Decimal nam = Convert.ToDecimal(namSinh);
                if (ngay < 1 || ngay > 366 || thang < 1 || thang > 12)
                {
                    lblNgaySinh.ForeColor = Color.Red;
                    return;
                }
                lblNgaySinh.ForeColor = Color.Black;
            }
            catch
            {
                lblNgaySinh.ForeColor = Color.Red;
                return;
            }
            switch(thangSinh)
            {
                case "01": thangSinh = "JAN"; break;
                case "02": thangSinh = "FEB"; break;
                case "03": thangSinh = "MAR"; break;
                case "04": thangSinh = "APR"; break;
                case "05": thangSinh = "MAY"; break;
                case "06": thangSinh = "JUN"; break;
                case "07": thangSinh = "JUL"; break;
                case "08": thangSinh = "AUG"; break;
                case "09": thangSinh = "SEP"; break;
                case "10": thangSinh = "OCT"; break;
                case "11": thangSinh = "NOV"; break;
                case "12": thangSinh = "DEC"; break;

            }
            string kqSinh = ngaySinh + "/" + thangSinh + "/" + namSinh;
            Class_Model model = new Class_Model();
            try
            {
                model.insertKhachHang(txtHoTen.Text, kqSinh, txtGioiTinh.Text,
                txtQuocTich.Text, dThoai, txtDiaChi.Text, txtMSCB.Text,
                txtCMND.Text, txtPassport.Text, txtThongTinKS.Text, txtMSNGH.Text);
            }
            catch
            {
                MessageBox.Show("THÔNG TIN ĐĂNG KÝ KHÔNG HỢP LỆ", "ERROR", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }
        /*************************************************************
         * Hàm cho khách hàng hủy đăng ký 
         * **********************************************************/
        private void btnCancel_Click(object sender, EventArgs e)
        {
            if (MessageBox.Show("Quý khách có muốn thoát không?", "THOÁT", MessageBoxButtons.YesNo, 
                MessageBoxIcon.Question) == DialogResult.Yes)
            {
                Close();
                //System.Windows.Forms.Application.Exit();
            }
        }

        private void Form_DangKyChuyenBay_Load(object sender, EventArgs e)
        {
            txtMSCB.Text = MSCB;
        }
    }
}
