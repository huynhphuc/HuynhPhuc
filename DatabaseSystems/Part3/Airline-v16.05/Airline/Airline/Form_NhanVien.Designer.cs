namespace Airline
{
    partial class Form_NhanVien
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
            this.dtInfo = new System.Windows.Forms.DataGridView();
            this.btChiTietKhachHang = new System.Windows.Forms.Button();
            this.btnCB = new System.Windows.Forms.Button();
            this.btnKHCB = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.dtInfo)).BeginInit();
            this.SuspendLayout();
            // 
            // dtInfo
            // 
            this.dtInfo.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dtInfo.Location = new System.Drawing.Point(41, 152);
            this.dtInfo.Name = "dtInfo";
            this.dtInfo.Size = new System.Drawing.Size(613, 188);
            this.dtInfo.TabIndex = 0;
            // 
            // btChiTietKhachHang
            // 
            this.btChiTietKhachHang.Location = new System.Drawing.Point(41, 12);
            this.btChiTietKhachHang.Name = "btChiTietKhachHang";
            this.btChiTietKhachHang.Size = new System.Drawing.Size(148, 34);
            this.btChiTietKhachHang.TabIndex = 1;
            this.btChiTietKhachHang.Text = "Chi tiết khách hàng";
            this.btChiTietKhachHang.UseVisualStyleBackColor = true;
            this.btChiTietKhachHang.Click += new System.EventHandler(this.btChiTietKhachHang_Click);
            // 
            // btnCB
            // 
            this.btnCB.Location = new System.Drawing.Point(41, 62);
            this.btnCB.Name = "btnCB";
            this.btnCB.Size = new System.Drawing.Size(227, 28);
            this.btnCB.TabIndex = 2;
            this.btnCB.Text = "Danh sách các chuyến bay từ thời điểm hiện tại:";
            this.btnCB.UseVisualStyleBackColor = true;
            this.btnCB.Click += new System.EventHandler(this.btnCB_Click);
            // 
            // btnKHCB
            // 
            this.btnKHCB.Location = new System.Drawing.Point(41, 106);
            this.btnKHCB.Name = "btnKHCB";
            this.btnKHCB.Size = new System.Drawing.Size(194, 23);
            this.btnKHCB.TabIndex = 3;
            this.btnKHCB.Text = "khách hàng đi chuyến bay";
            this.btnKHCB.UseVisualStyleBackColor = true;
            this.btnKHCB.Click += new System.EventHandler(this.btnKHCB_Click);
            // 
            // Form_NhanVien
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(692, 352);
            this.Controls.Add(this.btnKHCB);
            this.Controls.Add(this.btnCB);
            this.Controls.Add(this.btChiTietKhachHang);
            this.Controls.Add(this.dtInfo);
            this.Name = "Form_NhanVien";
            this.Text = "Chào bạn!";
            ((System.ComponentModel.ISupportInitialize)(this.dtInfo)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.DataGridView dtInfo;
        private System.Windows.Forms.Button btChiTietKhachHang;
        private System.Windows.Forms.Button btnCB;
        private System.Windows.Forms.Button btnKHCB;
    }
}