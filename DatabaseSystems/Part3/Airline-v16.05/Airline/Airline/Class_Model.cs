using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Oracle.DataAccess.Client;
using Oracle.DataAccess.Types;
using System.Data;
using System.Windows.Forms;
namespace Airline
{
    class Class_Model
    {
        public string username = "";
        public string password = "";
        public OracleDataAdapter myDA;
        public DataSet myDataSet;

        public Class_Model()
        {
        }
        /*********************************************************************
         * Hàm connect dùng cho nhân viên và khách hàng kết nối database
         * username lần lượt NhanVien Pass NHANVIEN, KhachHang KHACHHANG
         * ******************************************************************/
        public OracleConnection connect(string user, string password) 
        {
            string stringConnection = "Data Source=ORCL;User Id=" + user + ";Password=" + 
                password + ";";
            return new OracleConnection(stringConnection);
        }

        public OracleConnection connect()
        {
            string stringConnection = "Data Source=ORCL;User Id=ASS2;Password=ASS2;";
            OracleConnection cn = new OracleConnection(stringConnection);
   
            cn.ConnectionString = stringConnection;
            cn.Open();  // mở kết nối
            return cn;
        }
    
        public DataTable KhachHang_ChuyenBay(string username, string password)
        {
            DataTable MyTable = new DataTable();
            OracleCommand cmd = new OracleCommand();
            OracleConnection cn = connect(username, password);
            try
            {
                cn.Open();
                cmd.Connection = cn;

                // lấy lệnh
                cmd.CommandText = "SELECT * from ASS2.KhachHang";

                cmd.CommandType = CommandType.Text;
                //Sends the CommandText to the Connection and builds an OracleDataReader.
                OracleDataReader rd = cmd.ExecuteReader();
                //Fills a DataTable with values from a data source using the supplied IDataReader 
                //using an error-handling delegate.
                MyTable.Load(rd);
                cn.Close();
            }
            catch
            {
                MessageBox.Show("Bạn không có quyền sử dụng chức năng này!", "Vi phạm",
                    MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
            return MyTable;
        }
        /********************************************************************
         * Hàm để  Xem danh sách các chuyến bay
         * (tìm theo khoảng thời gian, mặc định từ thời điểm hiện tại)
         * *****************************************************************/
        public DataSet danhSachCB(DataGridView myDataGridView)
        {
            OracleConnection cn = connect();
            string sqlSelect = "SELECT *";
            sqlSelect += " FROM CHUYENBAY";
            sqlSelect += " WHERE SYSDATE < CHUYENBAY.THOIDIEMDI";

            OracleCommand cmd = new OracleCommand();
            cmd.CommandText = sqlSelect; // lấy lệnh
            cmd.Connection = cn;
            myDA = new OracleDataAdapter(cmd);

            OracleCommandBuilder builder = new OracleCommandBuilder(myDA);

            myDataSet = new DataSet();
            myDA.Fill(myDataSet, "Danh sách chuyến bay từ thời điểm hiện tại");

            myDataGridView.DataSource = myDataSet.Tables["Danh sách chuyến bay từ thời điểm hiện tại"].DefaultView;
            return myDataSet;
        }
        /********************************************************************
         * Hàm xem danh sách khách hàng đăng ký một chuyến bay nào đó
         * *****************************************************************/
        public DataSet khachHangDKChuyenBay(DataGridView myDataGridView)
        {
            OracleConnection cn = connect();
            string sqlSelect = "SELECT HOTEN, C.MSCB";
            sqlSelect += " FROM KHACHHANG K, CHUYENBAY C";
            sqlSelect += " WHERE K.MSCB = C.MSCB";

            OracleCommand cmd = new OracleCommand();
            cmd.CommandText = sqlSelect; // lấy lệnh
            cmd.Connection = cn;
            myDA = new OracleDataAdapter(cmd);

            OracleCommandBuilder builder = new OracleCommandBuilder(myDA);

            myDataSet = new DataSet();
            myDA.Fill(myDataSet, "Danh sách khách hàng DK chuyến bay");

            myDataGridView.DataSource = myDataSet.Tables["Danh sách khách hàng DK chuyến bay"].DefaultView;
            return myDataSet;
        } 
        /********************************************************************
         * Nếu khách hàng là người lớn dò bàng KHACHHANGNL
         * Nếu khách hàng là trẻ em dò bàng KHACHHANGTE
         * để tìm mã số tăng kế tiếp
         * *****************************************************************/
        public Int64 nextNumber (string CMND)
        {
            OracleConnection cn = connect();
            Int64 so = 1;   // khởi tạo số = 1
            OracleCommand cmdSelect = new OracleCommand(); // lấy MSKH so sánh để tăng biến số lên 
            cmdSelect.Connection = cn;

            if (CMND == "Không có") // trẻ em
            {
                cmdSelect.CommandText = "SELECT MSKH FROM KHACHHANGTE";
                cmdSelect.CommandType = CommandType.Text;
            }
            else
            {
                cmdSelect.CommandText = "SELECT MSKH FROM KHACHHANGNL";
                cmdSelect.CommandType = CommandType.Text;
            }
            OracleDataReader dr = cmdSelect.ExecuteReader();
            while (dr.Read())
            {
                if (so != Convert.ToInt64(dr.GetString(0).Remove(0, 2))) break; // lấy MSKH bỏ 2 ký tự đầu "NL" or "TE" rồi chuyển phần còn lại thành số Int64
                so++;
            }
            return so;
        }
        /********************************************************************
         * Hàm kiểm tra khách hàng có tồn tại chưa qua CMND khi insert
         * *****************************************************************/
        public bool checkCMND(string CMND)
        {
            OracleConnection cn = connect();
            OracleCommand cmdSelect = new OracleCommand();
            cmdSelect.Connection = cn;

            cmdSelect.CommandText = "SELECT CMND FROM KHACHHANGNL";
            cmdSelect.CommandType = CommandType.Text;
            OracleDataReader dr = cmdSelect.ExecuteReader();
            while (dr.Read())
            {
                if (CMND == dr.GetString(0)) return true;
            }
            return false;
        }

        /********************************************************************
         * Hàm kiểm tra xem mã số người giám hộ có tồn tại không
         * *****************************************************************/
        public bool checkMSNGH(string MSNGH)
        {
            OracleConnection cn = connect();
            OracleCommand cmdSelect = new OracleCommand();
            cmdSelect.Connection = cn;

            cmdSelect.CommandText = "SELECT MSKH FROM KHACHHANGNL";
            cmdSelect.CommandType = CommandType.Text;
            OracleDataReader dr = cmdSelect.ExecuteReader();
            while (dr.Read())
            {
                if (MSNGH == dr.GetString(0)) return true;
            }
            return false;
        }

        /********************************************************************
         * Hàm cho khách xem thông tin tuyến bay để chọn tuyến
         * *****************************************************************/
        public DataTable thongTinTBGa(DataTable dt)
        {
            OracleConnection cn = connect();
            string sqlSelect = "SELECT T.MSTB, GADI.TENSB ||', '|| GADI.THANHPHO ||', '|| GADI.QUOCGIA Ga_Di, GADEN.TENSB ||', '|| GADEN.THANHPHO ||', '|| GADEN.QUOCGIA Ga_Den ";
            sqlSelect += "FROM TUYENBAY T, GA GADI, GA GADEN ";
            sqlSelect += "WHERE T.MSG_DI = GADI.MSG AND T.MSG_DEN = GADEN.MSG";

            OracleCommand cmd = new OracleCommand();
            cmd.CommandText = sqlSelect; // lấy lệnh
            cmd.Connection = cn;
            myDA = new OracleDataAdapter(cmd);

            OracleCommandBuilder builder = new OracleCommandBuilder(myDA);

            myDataSet = new DataSet();
            myDA.Fill(myDataSet, "Thông tin tuyến bay và ga");
            cn.Dispose();

            return dt = myDataSet.Tables["Thông tin tuyến bay và ga"];
        }

        /********************************************************************
         * Hàm dùng để lấy danh sách chuyến bay ứng với 
         * tuyến bay vừa chọn
         * *****************************************************************/
        public DataTable danhSachCBMuonDi(DataTable dt, string maSo)
        {
            OracleConnection cn = connect();
            string sqlSelect = "SELECT * ";
            sqlSelect += " FROM CHUYENBAY";
            sqlSelect += " WHERE MSTB = :p_MSTB";

            OracleCommand cmd = new OracleCommand();
            cmd.CommandText = sqlSelect; // lấy lệnh
            cmd.Connection = cn;

            OracleParameter pMSTB = new OracleParameter();
            pMSTB.DbType = DbType.Decimal;
            pMSTB.Value = Convert.ToDecimal(maSo);
            pMSTB.ParameterName = "p_MSTB";

            cmd.Parameters.Add(pMSTB);
            myDA = new OracleDataAdapter(cmd);

            OracleCommandBuilder builder = new OracleCommandBuilder(myDA);

            myDataSet = new DataSet();
            myDA.Fill(myDataSet, "Thông tin chuyến bay ứng với tuyến bay");
            cn.Dispose();

            return dt = myDataSet.Tables["Thông tin chuyến bay ứng với tuyến bay"];
        }

        /********************************************************************
         * Hàm để khách hàng khai báo thông tin hàm này sẽ gọi
         * hàm insertKhachHangNL or hàm insertKhachHangTE để
         * insert vào 2 bàng KHACHHANGNL OR KHACHHANGTE tương ứng
         * và bảng KHACHHANG
         * ******************************************************************/
        public void insertKhachHang(string hoTen, string ngaySinh, string gioiTinh, 
            string quocTich, string SODT, string diaChi, string MSCB, string chungMinhND, 
            string passPort, string thongTinKS, string maSoNGH)      
        {
            if (checkCMND(chungMinhND))
            {
                MessageBox.Show("Khách hàng đã tồn tại!", "ERROR", MessageBoxButtons.OK);
                return;
            }
            if (maSoNGH == "")
            {

            }
            else if (!checkMSNGH(maSoNGH))
            {
                MessageBox.Show("Người giám hộ không tồn tại!", "ERROR", MessageBoxButtons.OK);
                return;
            }
            Int64 so;
            OracleConnection cn = connect();
            string sqlInsert = "INSERT INTO KHACHHANG ";
            sqlInsert += "VALUES (:p_MSKH, :p_HOTEN, :p_NGAYSINH, :p_GIOITINH, :p_QUOCTICH, :p_SODT, ";
            sqlInsert += " :p_DIACHI, :p_MSTTTG, :p_MSPHH, :p_MSCB, :P_KHOILUONGVUOT)";

            OracleCommand cmdInsert = new OracleCommand();
            cmdInsert.CommandText = sqlInsert;
            cmdInsert.Connection = cn;

            so = nextNumber(chungMinhND); // gọi hàm này để lấy số tự động tăng
            string maSoKH;
            string soTuDongTang = string.Format("{0:0000000000}", so); // chỉnh lại định dang. Vd: 1 -> 0000000001

            OracleParameter pMSKH = new OracleParameter();
            if (chungMinhND != "Không có") // kiểm tra người lớn hay trẻ em
            {
                pMSKH.Value = "NL";
                pMSKH.Value += soTuDongTang;
                pMSKH.ParameterName = "p_MSKH";

                maSoKH = "NL";
                maSoKH += soTuDongTang;
            }
            else
            {
                pMSKH.Value = "TE";
                pMSKH.Value += soTuDongTang;
                pMSKH.ParameterName = "p_MSKH";

                maSoKH = "TE";
                maSoKH += soTuDongTang;
            }

            OracleParameter pHOTEN = new OracleParameter();
            pHOTEN.Value = hoTen;
            pHOTEN.ParameterName = "p_HOTEN";

            OracleParameter pNGAYSINH = new OracleParameter();
            pNGAYSINH.Value = ngaySinh;
            pNGAYSINH.ParameterName = "p_NGAYSINH";

            OracleParameter pGIOITINH = new OracleParameter();
            pGIOITINH.Value = gioiTinh;
            pGIOITINH.ParameterName = "p_GIOITINH";

            OracleParameter pQUOCTICH = new OracleParameter();
            pQUOCTICH.Value = quocTich;
            pQUOCTICH.ParameterName = "p_QUOCTICH";

            OracleParameter pSODT = new OracleParameter();
            pSODT.Value = SODT;
            pSODT.ParameterName = "p_SODT";

            OracleParameter pDIACHI = new OracleParameter();
            pDIACHI.Value = diaChi;
            pDIACHI.ParameterName = "p_DIACHI";

            OracleParameter pMSTTTG = new OracleParameter();
            pMSTTTG.DbType = DbType.Decimal;
            pMSTTTG.Value = 1;
            pMSTTTG.ParameterName = "p_MSTTTG";

            OracleParameter pMSPHH = new OracleParameter();
            pMSPHH.DbType = DbType.Decimal;
            pMSPHH.Value = 1;
            pMSPHH.ParameterName = "p_MSPHH";

            OracleParameter pMSCB = new OracleParameter();
            pMSCB.Value = MSCB;
            pMSCB.ParameterName = "p_MSCB";

            OracleParameter pKHOILUONGVUOT = new OracleParameter();
            pKHOILUONGVUOT.DbType = DbType.Decimal;
            pKHOILUONGVUOT.Value = 0;
            pKHOILUONGVUOT.ParameterName = "p_KHOILUONGVUOT";

            try
            {
                cmdInsert.Parameters.Add(pMSKH);
                cmdInsert.Parameters.Add(pHOTEN);
                cmdInsert.Parameters.Add(pNGAYSINH);
                cmdInsert.Parameters.Add(pGIOITINH);
                cmdInsert.Parameters.Add(pQUOCTICH);
                cmdInsert.Parameters.Add(pSODT);
                cmdInsert.Parameters.Add(pDIACHI);
                cmdInsert.Parameters.Add(pMSTTTG);
                cmdInsert.Parameters.Add(pMSPHH);
                cmdInsert.Parameters.Add(pMSCB);
                cmdInsert.Parameters.Add(pKHOILUONGVUOT);

                cmdInsert.ExecuteNonQuery();
                cmdInsert.Dispose();

                if (chungMinhND != "Không có") insertKhachHangNL(maSoKH, chungMinhND, passPort);
                else insertKhachHangTE(maSoKH, thongTinKS, maSoNGH);

                MessageBox.Show("Quý khách đã đăng ký thành công. Chúc quý khách có 1 chuyến đi vui vẻ!!! Đây là Mã Số của quý khách: " + maSoKH,
                    "THÀNH CÔNG", MessageBoxButtons.OK);
            }
            catch
            {
                try
                {
                    deleteKhachHang(maSoKH);
                }
                catch
                {

                }
                MessageBox.Show("Chuyến bay đã hết chỗ, mời quý khách chọn chuyến khác hoặc passport không đúng!", "ERROR", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }
        }

        public void insertKhachHangNL(string maSoKH, string chungMinhND, string passport)
        {
            OracleConnection cn = connect();
            string sqlInsert = "INSERT INTO KHACHHANGNL ";
            sqlInsert += "VALUES (:p_MSKH, :p_CMND, :p_PASSPORT)";

            OracleCommand cmdInsert = new OracleCommand();
            cmdInsert.CommandText = sqlInsert;
            cmdInsert.Connection = cn;

            OracleParameter pMSKH = new OracleParameter();
            pMSKH.Value = maSoKH;
            pMSKH.ParameterName = "p_MSKH";

            OracleParameter pHOTEN = new OracleParameter();
            pHOTEN.Value = chungMinhND;
            pHOTEN.ParameterName = "p_CMND";

            OracleParameter pNGAYSINH = new OracleParameter();
            pNGAYSINH.Value = passport;
            pNGAYSINH.ParameterName = "p_PASSPORT";

            cmdInsert.Parameters.Add(pMSKH);
            cmdInsert.Parameters.Add(pHOTEN);
            cmdInsert.Parameters.Add(pNGAYSINH);

            cmdInsert.ExecuteNonQuery();      
            cmdInsert.Dispose();
        }

        public void insertKhachHangTE(string maSoKH, string thongTinKS, string maSoNGH)
        {
            OracleConnection cn = connect();
            string sqlInsert = "INSERT INTO KHACHHANGTE ";
            sqlInsert += "VALUES (:p_MSKH, :p_THONGTINKSINH, :p_MSNGH)";

            OracleCommand cmdInsert = new OracleCommand();
            cmdInsert.CommandText = sqlInsert;
            cmdInsert.Connection = cn;

            OracleParameter pMSKH = new OracleParameter();
            pMSKH.Value = maSoKH;
            pMSKH.ParameterName = "p_MSKH";

            OracleParameter pTHONGTINKS = new OracleParameter();
            pTHONGTINKS.Value = thongTinKS;
            pTHONGTINKS.ParameterName = "p_THONGTINKSINH";

            OracleParameter pMSNGH = new OracleParameter();
            pMSNGH.Value = maSoNGH;
            pMSNGH.ParameterName = "p_MSNGH";

            cmdInsert.Parameters.Add(pMSKH);
            cmdInsert.Parameters.Add(pTHONGTINKS);
            cmdInsert.Parameters.Add(pMSNGH);

            cmdInsert.ExecuteNonQuery();
            cmdInsert.Dispose();
        }
        /*********************************************************************
         * Hàm này để hàm insertKhachHang gọi nếu xảy ra lỗi ko insert
         * được vào bảng KHACHHANGNL OR KHACHHANGTE
         * thì sẽ cố gắng xóa dòng vừa được insert vào bảng KHACHHANG
         * ******************************************************************/
        public void deleteKhachHang(string maSoKH)
        {
            OracleConnection cn = connect();
            string sqlDelete = "DELETE FROM KHACHHANG WHERE MSKH = :p_MSKH";

            OracleCommand cmdDelete = new OracleCommand();
            cmdDelete.CommandText = sqlDelete;
            cmdDelete.Connection = cn;

            OracleParameter pMSKH = new OracleParameter();
            pMSKH.Value = maSoKH;
            pMSKH.ParameterName = "p_MSKH";

            cmdDelete.Parameters.Add(pMSKH);
            cmdDelete.ExecuteNonQuery();
            cmdDelete.Dispose();
        }
        /*********************************************************************
         * Hàm sau khi khách hàng xem thông tin cá nhân nếu thông tin sai
         * thì sẽ update. Hàm này cũng sẽ gọi hàm updateKHACHHANGNL
         * or hàm updateKHACHHANGTE để update ở 2 bảng KHACHHANGNL or
         * KHACHHANGTE tương ứng với người lớn/trẻ em
         * *******************************************************************/
        public void updateKhachHang(string maSoKH, string hoTen, string ngaySinh, string gioiTinh, 
            string quocTich, string SODT, string diaChi, string MSCB, Double khoiLuongV, 
            string chungMinhND, string passPort, string thongTinKS, string maSoNGH)
        {
            if (maSoNGH == "")
            {

            }
            else if (!checkMSNGH(maSoNGH))
            {
                MessageBox.Show("Người giám hộ không tồn tại!", "ERROR", MessageBoxButtons.OK);
                return;
            }
            SODT = "+84" + SODT.Remove(0, 2);

            OracleConnection cn = connect();
            string sqlUpdate = "UPDATE KHACHHANG ";
            sqlUpdate += "SET HOTEN = '"+hoTen+"', NGAYSINH = '"+ngaySinh+"', GIOITINH = '"+gioiTinh+"', ";
            sqlUpdate += "QUOCTICH = '"+quocTich+"', SODT = '"+SODT+"', DIACHI = '"+diaChi+"', ";
            sqlUpdate += "MSCB = '"+MSCB+"', KHOILUONGVUOT = '"+khoiLuongV+"' ";
            sqlUpdate += "WHERE MSKH = '"+maSoKH+"'";

            OracleCommand cmdUpdate = new OracleCommand();
            cmdUpdate.CommandText = sqlUpdate;
            cmdUpdate.Connection = cn;

            
            try
            {
                cmdUpdate.ExecuteNonQuery();
                cmdUpdate.Dispose();

                string _maSoKH = maSoKH;
                string remain = maSoKH.Remove(2, 10);  // Chỉ lấy "NL" hay "TE" trong MSKH
                if (remain == "NL") updateKHACHHANGNL(_maSoKH, chungMinhND, passPort);
                else updateKHACHHANGTE(_maSoKH, thongTinKS, maSoNGH);
            }
            catch
            {
                MessageBox.Show("NGÀY SINH KHÔNG HỢP LỆ!", "ERROR", MessageBoxButtons.OK, MessageBoxIcon.Error);

            }
        }

        public void updateKHACHHANGNL(string maSoKH, string chungMinhND, string passport)
        {
            OracleConnection cn = connect();
            string sqlUpdate = "UPDATE KHACHHANGNL ";
            sqlUpdate += "SET CMND = '"+chungMinhND+"', PASSPORT = '"+passport+"' ";
            sqlUpdate += "WHERE MSKH = '"+maSoKH+"'";

            OracleCommand cmdUpdate = new OracleCommand();
            cmdUpdate.CommandText = sqlUpdate;
            cmdUpdate.Connection = cn;
            try
            {
                cmdUpdate.ExecuteNonQuery();

                cmdUpdate.Dispose();
                MessageBox.Show("Quý khách đã update thành công!!!", "THÀNH CÔNG", MessageBoxButtons.OK);
            }
            catch
            {
                MessageBox.Show("CMND KHÔNG HỢP LỆ!", "ERROR", MessageBoxButtons.OK, MessageBoxIcon.Error);
            }         
        }

        public void updateKHACHHANGTE(string maSoKH, string thongTinKS, string maSoNGH)
        {
            OracleConnection cn = connect();
            string sqlUpdate = "UPDATE KHACHHANGTE ";
            sqlUpdate += "SET THONGTINKSINH = '"+thongTinKS+"', MSNGH = '"+maSoNGH+"' ";
            sqlUpdate += "WHERE MSKH = '"+maSoKH+"'";

            OracleCommand cmdUpdate = new OracleCommand();
            cmdUpdate.CommandText = sqlUpdate;
            cmdUpdate.Connection = cn;

            try
            {
                cmdUpdate.ExecuteNonQuery();

                cmdUpdate.Dispose();
                MessageBox.Show("Quý khách đã update thành công!!!", "THÀNH CÔNG", MessageBoxButtons.OK);
            }
            catch
            {
                MessageBox.Show("THÔNG TIN KHÔNG HỢP LỆ!", "ERROR", MessageBoxButtons.OK, MessageBoxIcon.Error);

            }
        }
        /***********************************************************************
         * Hàm để khách hàng "NL" xem lại thông tin cá nhân sau khi 
         * đăng ký
         * ********************************************************************/
        public DataTable thongTinKHNL(DataTable dt, string maSo)
        {
            OracleConnection cn = connect();
            string sqlSelect = "SELECT K.MSKH, K.HOTEN, K.NGAYSINH, K.GIOITINH, K.QUOCTICH, ";
            sqlSelect += " K.SODT, K.DIACHI, K.MSPHH, K.MSCB, K.KHOILUONGVUOT, NL.CMND, NL.PASSPORT ";
            sqlSelect += " FROM KHACHHANG K, KHACHHANGNL NL";
            sqlSelect += " WHERE K.MSKH = NL.MSKH AND K.MSKH = :p_MSKH";

            OracleCommand cmd = new OracleCommand();
            cmd.CommandText = sqlSelect; // lấy lệnh
            cmd.Connection = cn;
            myDA = new OracleDataAdapter(cmd);

            OracleParameter pMSKH = new OracleParameter();
            pMSKH.Value = maSo;
            pMSKH.ParameterName = "p_MSKH";
            cmd.Parameters.Add(pMSKH);

            OracleCommandBuilder builder = new OracleCommandBuilder(myDA);

            myDataSet = new DataSet();
            myDA.Fill(myDataSet, "Thông tin khách hàng");
            cn.Dispose();

            return dt = myDataSet.Tables["Thông tin khách hàng"];
        }
        /***********************************************************************
         * Hàm để khách hàng "TE" xem lại thông tin cá nhân sau khi 
         * đăng ký
         * ********************************************************************/
        public DataTable thongTinKHTE(DataTable dt, string maSo)
        {
            OracleConnection cn = connect();
            string sqlSelect = "SELECT K.MSKH, K.HOTEN, K.NGAYSINH, K.GIOITINH, K.QUOCTICH, K.SODT, ";
            sqlSelect += " K.DIACHI, K.MSPHH, K.MSCB, K.KHOILUONGVUOT, TE.THONGTINKSINH, TE.MSNGH ";
            sqlSelect += " FROM KHACHHANG K, KHACHHANGTE TE";
            sqlSelect += " WHERE K.MSKH = TE.MSKH AND K.MSKH = :p_MSKH";

            OracleCommand cmd = new OracleCommand();
            cmd.CommandText = sqlSelect; // lấy lệnh
            cmd.Connection = cn;
            myDA = new OracleDataAdapter(cmd);

            OracleParameter pMSKH = new OracleParameter();
            pMSKH.Value = maSo;
            pMSKH.ParameterName = "p_MSKH";
            cmd.Parameters.Add(pMSKH);

            OracleCommandBuilder builder = new OracleCommandBuilder(myDA);

            myDataSet = new DataSet();
            myDA.Fill(myDataSet, "Thông tin khách hàng");
            cn.Dispose();

            return dt = myDataSet.Tables["Thông tin khách hàng"];
        }

        /************************************************************************
         * Hàm cho nhân viên xem danh sách ghế trống
         * *********************************************************************/
        public DataSet danhsachghetrong(string msCB, DataGridView myDataGridView)
        {
            OracleConnection cn = connect();
            string ghetrong = "SELECT * FROM GheNgoi  GN WHERE GN.MSLMB IN (SELECT MB.MSLMB FROM ChuyenBay  CB,  MayBay MB WHERE CB.MSMB = MB.MSMB AND CB.MSCB='" + msCB + "') AND GN.GheSo NOT IN (SELECT GK.GheSo FROM GheKhach GK WHERE GK.MSKH IN (SELECT KH.MSKH FROM KhachHang  KH, TrangThaiTG TT WHERE KH.MSCB = '" + msCB + "' AND KH.MSTTTG = TT.MSTTTG AND TT.TenTT = 'TG' ) )";
            OracleCommand cmd = new OracleCommand();
            cmd.CommandText = ghetrong; // lấy lệnh
            cmd.Connection = cn;
            myDA = new OracleDataAdapter(cmd);

            OracleCommandBuilder builder = new OracleCommandBuilder(myDA);

            myDataSet = new DataSet();
            myDA.Fill(myDataSet, "Danh sach ghe trong");

            myDataGridView.DataSource = myDataSet.Tables["Danh sach ghe trong"].DefaultView;
            return myDataSet;
        }
        /************************************************************************
         * Hàm để hiện thông tin khách hàng vào textBox sau khi khách 
         * hàng nhấn vào button Đồng Ý
         * *********************************************************************/
        public void showThongTinKHInTextBox(string maSoKH, TextBox txtHoTen, TextBox txtNgaySinh,
            TextBox  txtGioiTinh, TextBox txtQuocTich, TextBox txtSoDT, TextBox txtDiaChi,
            TextBox txtMSCB, TextBox txtKhoiLuongVuot, TextBox txtCMND, TextBox txtPassPort, 
            TextBox txtThongTinKS, TextBox txtMSNGH)
        {
            OracleConnection cn = connect();
            OracleCommand cmdSelect = new OracleCommand();
            cmdSelect.Connection = cn;

            cmdSelect.CommandText = "SELECT HOTEN, NGAYSINH, GIOITINH, QUOCTICH, SODT, DIACHI, "
                + "MSCB, KHOILUONGVUOT "
                + "FROM KHACHHANG "
                + "WHERE MSKH = '" + maSoKH + "'";

            cmdSelect.CommandType = CommandType.Text;

            OracleDataReader dr = cmdSelect.ExecuteReader();
            while (dr.Read())
            {
                txtHoTen.Text = dr.GetString(0);
                //txtNgaySinh.Text = Convert.ToString(dr.GetOracleDate(1).Day) + "/" + Convert.ToString(dr.GetOracleDate(1).Month) + "/" + Convert.ToString(dr.GetOracleDate(1).Year);
                txtNgaySinh.Text = Convert.ToString(dr.GetOracleDate(1));
                txtGioiTinh.Text = dr.GetString(2);
                txtQuocTich.Text = dr.GetString(3);
                txtSoDT.Text = "09" + dr.GetString(4).Remove(0, 3);
                txtDiaChi.Text = dr.GetString(5);
                txtMSCB.Text = dr.GetString(6);
                txtKhoiLuongVuot.Text = Convert.ToString(dr.GetOracleDecimal(7));
            }

            cmdSelect.CommandText = "SELECT CMND, PASSPORT "
                + "FROM KHACHHANGNL "
                + "WHERE MSKH = '" + maSoKH + "'";

            cmdSelect.CommandType = CommandType.Text;

            OracleDataReader drNL = cmdSelect.ExecuteReader();
            while (drNL.Read())
            {
                txtCMND.Text = drNL.GetString(0);
                txtPassPort.Text = drNL.GetString(1);
            }

            cmdSelect.CommandText = "SELECT THONGTINKSINH, MSNGH "
                + "FROM KHACHHANGTE "
                + "WHERE MSKH = '" + maSoKH + "'";

            cmdSelect.CommandType = CommandType.Text;

            OracleDataReader drTE = cmdSelect.ExecuteReader();
            while (drTE.Read())
            {
                txtThongTinKS.Text = drTE.GetString(0);
                txtMSNGH.Text = drTE.GetString(1);
                txtCMND.Text = "Không có";
                txtPassPort.Text = "Không có";
            }
        }
    }
}