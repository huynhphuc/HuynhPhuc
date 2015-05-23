namespace Airline
{
    partial class Form_KhachHang
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.btnOK = new System.Windows.Forms.Button();
            this.label2 = new System.Windows.Forms.Label();
            this.txtMSKH = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.gbUpdate = new System.Windows.Forms.GroupBox();
            this.txtDiaChi = new System.Windows.Forms.TextBox();
            this.lblDiaChi = new System.Windows.Forms.Label();
            this.txtMSNGH = new System.Windows.Forms.TextBox();
            this.lblMSNGH = new System.Windows.Forms.Label();
            this.txtThongTinKS = new System.Windows.Forms.TextBox();
            this.lblThongTinKS = new System.Windows.Forms.Label();
            this.txtPassport = new System.Windows.Forms.TextBox();
            this.lblPassport = new System.Windows.Forms.Label();
            this.txtCMND = new System.Windows.Forms.TextBox();
            this.lblCMND = new System.Windows.Forms.Label();
            this.txtKLVuot = new System.Windows.Forms.TextBox();
            this.lblKLVuot = new System.Windows.Forms.Label();
            this.txtMSCB = new System.Windows.Forms.TextBox();
            this.lblMSCB = new System.Windows.Forms.Label();
            this.txtSoDT = new System.Windows.Forms.TextBox();
            this.lblSoDT = new System.Windows.Forms.Label();
            this.txtQuocTich = new System.Windows.Forms.TextBox();
            this.lblQuocTich = new System.Windows.Forms.Label();
            this.txtGioiTinh = new System.Windows.Forms.TextBox();
            this.lblGioiTinh = new System.Windows.Forms.Label();
            this.txtNgaySinh = new System.Windows.Forms.TextBox();
            this.lblNgaySinh = new System.Windows.Forms.Label();
            this.txtHoTen = new System.Windows.Forms.TextBox();
            this.lblHoTen = new System.Windows.Forms.Label();
            this.btnSua = new System.Windows.Forms.Button();
            this.btnHuy = new System.Windows.Forms.Button();
            this.lvUpdateKH = new System.Windows.Forms.ListView();
            this.lblDongY = new System.Windows.Forms.Label();
            this.btnDongY = new System.Windows.Forms.Button();
            this.gbUpdate.SuspendLayout();
            this.SuspendLayout();
            // 
            // btnOK
            // 
            this.btnOK.Location = new System.Drawing.Point(478, 35);
            this.btnOK.Name = "btnOK";
            this.btnOK.Size = new System.Drawing.Size(75, 23);
            this.btnOK.TabIndex = 8;
            this.btnOK.Text = "XÁC NHẬN";
            this.btnOK.UseVisualStyleBackColor = true;
            this.btnOK.Click += new System.EventHandler(this.btnOK_Click);
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Location = new System.Drawing.Point(43, 45);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(354, 13);
            this.label2.TabIndex = 7;
            this.label2.Text = "Để xem thông tin cá nhân đã đăng ký mời quý khách nhấn nút xác nhận:";
            // 
            // txtMSKH
            // 
            this.txtMSKH.Location = new System.Drawing.Point(290, 9);
            this.txtMSKH.Name = "txtMSKH";
            this.txtMSKH.Size = new System.Drawing.Size(155, 20);
            this.txtMSKH.TabIndex = 6;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(43, 16);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(212, 13);
            this.label1.TabIndex = 5;
            this.label1.Text = "Mời quý khách nhập mã số của quý khách:";
            // 
            // gbUpdate
            // 
            this.gbUpdate.BackgroundImageLayout = System.Windows.Forms.ImageLayout.Zoom;
            this.gbUpdate.Controls.Add(this.txtDiaChi);
            this.gbUpdate.Controls.Add(this.lblDiaChi);
            this.gbUpdate.Controls.Add(this.txtMSNGH);
            this.gbUpdate.Controls.Add(this.lblMSNGH);
            this.gbUpdate.Controls.Add(this.txtThongTinKS);
            this.gbUpdate.Controls.Add(this.lblThongTinKS);
            this.gbUpdate.Controls.Add(this.txtPassport);
            this.gbUpdate.Controls.Add(this.lblPassport);
            this.gbUpdate.Controls.Add(this.txtCMND);
            this.gbUpdate.Controls.Add(this.lblCMND);
            this.gbUpdate.Controls.Add(this.txtKLVuot);
            this.gbUpdate.Controls.Add(this.lblKLVuot);
            this.gbUpdate.Controls.Add(this.txtMSCB);
            this.gbUpdate.Controls.Add(this.lblMSCB);
            this.gbUpdate.Controls.Add(this.txtSoDT);
            this.gbUpdate.Controls.Add(this.lblSoDT);
            this.gbUpdate.Controls.Add(this.txtQuocTich);
            this.gbUpdate.Controls.Add(this.lblQuocTich);
            this.gbUpdate.Controls.Add(this.txtGioiTinh);
            this.gbUpdate.Controls.Add(this.lblGioiTinh);
            this.gbUpdate.Controls.Add(this.txtNgaySinh);
            this.gbUpdate.Controls.Add(this.lblNgaySinh);
            this.gbUpdate.Controls.Add(this.txtHoTen);
            this.gbUpdate.Controls.Add(this.lblHoTen);
            this.gbUpdate.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.gbUpdate.Location = new System.Drawing.Point(46, 170);
            this.gbUpdate.Name = "gbUpdate";
            this.gbUpdate.Size = new System.Drawing.Size(667, 497);
            this.gbUpdate.TabIndex = 10;
            this.gbUpdate.TabStop = false;
            this.gbUpdate.Text = "Mời quý khách nhập lại thông tin:";
            // 
            // txtDiaChi
            // 
            this.txtDiaChi.Location = new System.Drawing.Point(126, 216);
            this.txtDiaChi.Name = "txtDiaChi";
            this.txtDiaChi.Size = new System.Drawing.Size(509, 20);
            this.txtDiaChi.TabIndex = 23;
            // 
            // lblDiaChi
            // 
            this.lblDiaChi.AutoSize = true;
            this.lblDiaChi.Location = new System.Drawing.Point(25, 223);
            this.lblDiaChi.Name = "lblDiaChi";
            this.lblDiaChi.Size = new System.Drawing.Size(43, 13);
            this.lblDiaChi.TabIndex = 22;
            this.lblDiaChi.Text = "Địa chỉ:";
            // 
            // txtMSNGH
            // 
            this.txtMSNGH.Location = new System.Drawing.Point(126, 463);
            this.txtMSNGH.Name = "txtMSNGH";
            this.txtMSNGH.Size = new System.Drawing.Size(509, 20);
            this.txtMSNGH.TabIndex = 21;
            // 
            // lblMSNGH
            // 
            this.lblMSNGH.AutoSize = true;
            this.lblMSNGH.Location = new System.Drawing.Point(25, 470);
            this.lblMSNGH.Name = "lblMSNGH";
            this.lblMSNGH.Size = new System.Drawing.Size(108, 13);
            this.lblMSNGH.TabIndex = 20;
            this.lblMSNGH.Text = "Mã số người giám hộ:";
            // 
            // txtThongTinKS
            // 
            this.txtThongTinKS.Location = new System.Drawing.Point(126, 420);
            this.txtThongTinKS.Name = "txtThongTinKS";
            this.txtThongTinKS.Size = new System.Drawing.Size(509, 20);
            this.txtThongTinKS.TabIndex = 19;
            // 
            // lblThongTinKS
            // 
            this.lblThongTinKS.AutoSize = true;
            this.lblThongTinKS.Location = new System.Drawing.Point(25, 427);
            this.lblThongTinKS.Name = "lblThongTinKS";
            this.lblThongTinKS.Size = new System.Drawing.Size(100, 13);
            this.lblThongTinKS.TabIndex = 18;
            this.lblThongTinKS.Text = "Thông tin khai sinh:";
            // 
            // txtPassport
            // 
            this.txtPassport.Location = new System.Drawing.Point(126, 380);
            this.txtPassport.Name = "txtPassport";
            this.txtPassport.Size = new System.Drawing.Size(509, 20);
            this.txtPassport.TabIndex = 17;
            // 
            // lblPassport
            // 
            this.lblPassport.AutoSize = true;
            this.lblPassport.Location = new System.Drawing.Point(25, 387);
            this.lblPassport.Name = "lblPassport";
            this.lblPassport.Size = new System.Drawing.Size(51, 13);
            this.lblPassport.TabIndex = 16;
            this.lblPassport.Text = "Passport:";
            // 
            // txtCMND
            // 
            this.txtCMND.Location = new System.Drawing.Point(126, 337);
            this.txtCMND.Name = "txtCMND";
            this.txtCMND.Size = new System.Drawing.Size(509, 20);
            this.txtCMND.TabIndex = 15;
            // 
            // lblCMND
            // 
            this.lblCMND.AutoSize = true;
            this.lblCMND.Location = new System.Drawing.Point(25, 344);
            this.lblCMND.Name = "lblCMND";
            this.lblCMND.Size = new System.Drawing.Size(42, 13);
            this.lblCMND.TabIndex = 14;
            this.lblCMND.Text = "CMND:";
            // 
            // txtKLVuot
            // 
            this.txtKLVuot.Location = new System.Drawing.Point(126, 296);
            this.txtKLVuot.Name = "txtKLVuot";
            this.txtKLVuot.Size = new System.Drawing.Size(509, 20);
            this.txtKLVuot.TabIndex = 13;
            // 
            // lblKLVuot
            // 
            this.lblKLVuot.AutoSize = true;
            this.lblKLVuot.Location = new System.Drawing.Point(25, 303);
            this.lblKLVuot.Name = "lblKLVuot";
            this.lblKLVuot.Size = new System.Drawing.Size(84, 13);
            this.lblKLVuot.TabIndex = 12;
            this.lblKLVuot.Text = "Khối lượng vượt:";
            // 
            // txtMSCB
            // 
            this.txtMSCB.Location = new System.Drawing.Point(126, 257);
            this.txtMSCB.Name = "txtMSCB";
            this.txtMSCB.Size = new System.Drawing.Size(509, 20);
            this.txtMSCB.TabIndex = 11;
            // 
            // lblMSCB
            // 
            this.lblMSCB.AutoSize = true;
            this.lblMSCB.Location = new System.Drawing.Point(25, 264);
            this.lblMSCB.Name = "lblMSCB";
            this.lblMSCB.Size = new System.Drawing.Size(97, 13);
            this.lblMSCB.TabIndex = 10;
            this.lblMSCB.Text = "Mã số chuyến bay:";
            // 
            // txtSoDT
            // 
            this.txtSoDT.Location = new System.Drawing.Point(126, 179);
            this.txtSoDT.Name = "txtSoDT";
            this.txtSoDT.Size = new System.Drawing.Size(509, 20);
            this.txtSoDT.TabIndex = 9;
            // 
            // lblSoDT
            // 
            this.lblSoDT.AutoSize = true;
            this.lblSoDT.Location = new System.Drawing.Point(25, 186);
            this.lblSoDT.Name = "lblSoDT";
            this.lblSoDT.Size = new System.Drawing.Size(73, 13);
            this.lblSoDT.TabIndex = 8;
            this.lblSoDT.Text = "Số điện thoại:";
            // 
            // txtQuocTich
            // 
            this.txtQuocTich.Location = new System.Drawing.Point(126, 142);
            this.txtQuocTich.Name = "txtQuocTich";
            this.txtQuocTich.Size = new System.Drawing.Size(509, 20);
            this.txtQuocTich.TabIndex = 7;
            // 
            // lblQuocTich
            // 
            this.lblQuocTich.AutoSize = true;
            this.lblQuocTich.Location = new System.Drawing.Point(25, 149);
            this.lblQuocTich.Name = "lblQuocTich";
            this.lblQuocTich.Size = new System.Drawing.Size(56, 13);
            this.lblQuocTich.TabIndex = 6;
            this.lblQuocTich.Text = "Quốc tịch:";
            // 
            // txtGioiTinh
            // 
            this.txtGioiTinh.Location = new System.Drawing.Point(126, 105);
            this.txtGioiTinh.Name = "txtGioiTinh";
            this.txtGioiTinh.Size = new System.Drawing.Size(509, 20);
            this.txtGioiTinh.TabIndex = 5;
            // 
            // lblGioiTinh
            // 
            this.lblGioiTinh.AutoSize = true;
            this.lblGioiTinh.Location = new System.Drawing.Point(25, 112);
            this.lblGioiTinh.Name = "lblGioiTinh";
            this.lblGioiTinh.Size = new System.Drawing.Size(47, 13);
            this.lblGioiTinh.TabIndex = 4;
            this.lblGioiTinh.Text = "Giới tính";
            // 
            // txtNgaySinh
            // 
            this.txtNgaySinh.Location = new System.Drawing.Point(126, 65);
            this.txtNgaySinh.Name = "txtNgaySinh";
            this.txtNgaySinh.Size = new System.Drawing.Size(509, 20);
            this.txtNgaySinh.TabIndex = 3;
            // 
            // lblNgaySinh
            // 
            this.lblNgaySinh.AutoSize = true;
            this.lblNgaySinh.Location = new System.Drawing.Point(25, 72);
            this.lblNgaySinh.Name = "lblNgaySinh";
            this.lblNgaySinh.Size = new System.Drawing.Size(57, 13);
            this.lblNgaySinh.TabIndex = 2;
            this.lblNgaySinh.Text = "Ngày sinh:";
            // 
            // txtHoTen
            // 
            this.txtHoTen.Location = new System.Drawing.Point(126, 27);
            this.txtHoTen.Name = "txtHoTen";
            this.txtHoTen.Size = new System.Drawing.Size(509, 20);
            this.txtHoTen.TabIndex = 1;
            // 
            // lblHoTen
            // 
            this.lblHoTen.AutoSize = true;
            this.lblHoTen.Location = new System.Drawing.Point(25, 34);
            this.lblHoTen.Name = "lblHoTen";
            this.lblHoTen.Size = new System.Drawing.Size(42, 13);
            this.lblHoTen.TabIndex = 0;
            this.lblHoTen.Text = "Họ tên:";
            // 
            // btnSua
            // 
            this.btnSua.Location = new System.Drawing.Point(245, 673);
            this.btnSua.Name = "btnSua";
            this.btnSua.Size = new System.Drawing.Size(75, 23);
            this.btnSua.TabIndex = 11;
            this.btnSua.Text = "SỬA";
            this.btnSua.UseVisualStyleBackColor = true;
            this.btnSua.Click += new System.EventHandler(this.btnSua_Click);
            // 
            // btnHuy
            // 
            this.btnHuy.Location = new System.Drawing.Point(379, 673);
            this.btnHuy.Name = "btnHuy";
            this.btnHuy.Size = new System.Drawing.Size(75, 23);
            this.btnHuy.TabIndex = 12;
            this.btnHuy.Text = "HỦY";
            this.btnHuy.UseVisualStyleBackColor = true;
            this.btnHuy.Click += new System.EventHandler(this.btnHuy_Click);
            // 
            // lvUpdateKH
            // 
            this.lvUpdateKH.Location = new System.Drawing.Point(42, 61);
            this.lvUpdateKH.Name = "lvUpdateKH";
            this.lvUpdateKH.Size = new System.Drawing.Size(666, 74);
            this.lvUpdateKH.TabIndex = 13;
            this.lvUpdateKH.UseCompatibleStateImageBehavior = false;
            // 
            // lblDongY
            // 
            this.lblDongY.AutoSize = true;
            this.lblDongY.Location = new System.Drawing.Point(43, 141);
            this.lblDongY.Name = "lblDongY";
            this.lblDongY.Size = new System.Drawing.Size(277, 13);
            this.lblDongY.TabIndex = 14;
            this.lblDongY.Text = "Nếu quý khách muốn sửa đổi thông tin mời nhấn Đồng ý:";
            // 
            // btnDongY
            // 
            this.btnDongY.Location = new System.Drawing.Point(478, 141);
            this.btnDongY.Name = "btnDongY";
            this.btnDongY.Size = new System.Drawing.Size(75, 23);
            this.btnDongY.TabIndex = 15;
            this.btnDongY.Text = "Đồng Ý";
            this.btnDongY.UseVisualStyleBackColor = true;
            this.btnDongY.Click += new System.EventHandler(this.btnDongY_Click);
            // 
            // Form_KhachHang
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(751, 741);
            this.Controls.Add(this.btnDongY);
            this.Controls.Add(this.lblDongY);
            this.Controls.Add(this.lvUpdateKH);
            this.Controls.Add(this.btnHuy);
            this.Controls.Add(this.btnSua);
            this.Controls.Add(this.gbUpdate);
            this.Controls.Add(this.btnOK);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.txtMSKH);
            this.Controls.Add(this.label1);
            this.Name = "Form_KhachHang";
            this.Text = "CHÀO MỪNG QUÝ KHÁCH ĐÃ TRỞ LẠI VỚI HÃNG HÀNG KHÔNG CHÚNG TÔI";
            this.Load += new System.EventHandler(this.Form_KhachHang_Load);
            this.gbUpdate.ResumeLayout(false);
            this.gbUpdate.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Button btnOK;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.TextBox txtMSKH;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.GroupBox gbUpdate;
        private System.Windows.Forms.TextBox txtMSNGH;
        private System.Windows.Forms.Label lblMSNGH;
        private System.Windows.Forms.TextBox txtThongTinKS;
        private System.Windows.Forms.Label lblThongTinKS;
        private System.Windows.Forms.TextBox txtPassport;
        private System.Windows.Forms.Label lblPassport;
        private System.Windows.Forms.TextBox txtCMND;
        private System.Windows.Forms.Label lblCMND;
        private System.Windows.Forms.TextBox txtKLVuot;
        private System.Windows.Forms.Label lblKLVuot;
        private System.Windows.Forms.TextBox txtMSCB;
        private System.Windows.Forms.Label lblMSCB;
        private System.Windows.Forms.TextBox txtSoDT;
        private System.Windows.Forms.Label lblSoDT;
        private System.Windows.Forms.TextBox txtQuocTich;
        private System.Windows.Forms.Label lblQuocTich;
        private System.Windows.Forms.TextBox txtGioiTinh;
        private System.Windows.Forms.Label lblGioiTinh;
        private System.Windows.Forms.TextBox txtNgaySinh;
        private System.Windows.Forms.Label lblNgaySinh;
        private System.Windows.Forms.TextBox txtHoTen;
        private System.Windows.Forms.Label lblHoTen;
        private System.Windows.Forms.Button btnSua;
        private System.Windows.Forms.Button btnHuy;
        private System.Windows.Forms.TextBox txtDiaChi;
        private System.Windows.Forms.Label lblDiaChi;
        private System.Windows.Forms.ListView lvUpdateKH;
        private System.Windows.Forms.Label lblDongY;
        private System.Windows.Forms.Button btnDongY;
    }
}