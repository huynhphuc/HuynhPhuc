namespace Airline
{
    partial class Form_KHDangKy
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
            this.label1 = new System.Windows.Forms.Label();
            this.txtMSTB = new System.Windows.Forms.TextBox();
            this.btnOK = new System.Windows.Forms.Button();
            this.groupBox3 = new System.Windows.Forms.GroupBox();
            this.btnNext = new System.Windows.Forms.Button();
            this.lvTBGa = new System.Windows.Forms.ListView();
            this.gbxTBGa = new System.Windows.Forms.GroupBox();
            this.lvCBDaChon = new System.Windows.Forms.ListView();
            this.groupBox3.SuspendLayout();
            this.gbxTBGa.SuspendLayout();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Location = new System.Drawing.Point(9, 200);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(236, 13);
            this.label1.TabIndex = 3;
            this.label1.Text = "Mời quý khách nhập Mã Số Tuyến Bay muốn đi:";
            // 
            // txtMSTB
            // 
            this.txtMSTB.Location = new System.Drawing.Point(12, 226);
            this.txtMSTB.Name = "txtMSTB";
            this.txtMSTB.Size = new System.Drawing.Size(200, 20);
            this.txtMSTB.TabIndex = 4;
            // 
            // btnOK
            // 
            this.btnOK.Location = new System.Drawing.Point(306, 220);
            this.btnOK.Name = "btnOK";
            this.btnOK.Size = new System.Drawing.Size(109, 26);
            this.btnOK.TabIndex = 5;
            this.btnOK.Text = "Xác Nhận";
            this.btnOK.UseVisualStyleBackColor = true;
            this.btnOK.Click += new System.EventHandler(this.btnOK_Click);
            // 
            // groupBox3
            // 
            this.groupBox3.Controls.Add(this.lvCBDaChon);
            this.groupBox3.Location = new System.Drawing.Point(18, 270);
            this.groupBox3.Name = "groupBox3";
            this.groupBox3.Size = new System.Drawing.Size(718, 146);
            this.groupBox3.TabIndex = 6;
            this.groupBox3.TabStop = false;
            this.groupBox3.Text = "Danh sách các chuyến bay ứng với tuyến bay quý khách đã chọn:";
            // 
            // btnNext
            // 
            this.btnNext.Location = new System.Drawing.Point(306, 434);
            this.btnNext.Name = "btnNext";
            this.btnNext.Size = new System.Drawing.Size(109, 26);
            this.btnNext.TabIndex = 7;
            this.btnNext.Text = "Next";
            this.btnNext.UseVisualStyleBackColor = true;
            this.btnNext.Click += new System.EventHandler(this.btnNext_Click);
            // 
            // lvTBGa
            // 
            this.lvTBGa.Location = new System.Drawing.Point(6, 21);
            this.lvTBGa.Name = "lvTBGa";
            this.lvTBGa.Size = new System.Drawing.Size(706, 119);
            this.lvTBGa.TabIndex = 8;
            this.lvTBGa.UseCompatibleStateImageBehavior = false;
            // 
            // gbxTBGa
            // 
            this.gbxTBGa.Controls.Add(this.lvTBGa);
            this.gbxTBGa.Location = new System.Drawing.Point(18, 29);
            this.gbxTBGa.Name = "gbxTBGa";
            this.gbxTBGa.Size = new System.Drawing.Size(717, 140);
            this.gbxTBGa.TabIndex = 10;
            this.gbxTBGa.TabStop = false;
            this.gbxTBGa.Text = "Thông tin các tuyến bay của hãng:";
            // 
            // lvCBDaChon
            // 
            this.lvCBDaChon.Location = new System.Drawing.Point(6, 27);
            this.lvCBDaChon.Name = "lvCBDaChon";
            this.lvCBDaChon.Size = new System.Drawing.Size(706, 119);
            this.lvCBDaChon.TabIndex = 9;
            this.lvCBDaChon.UseCompatibleStateImageBehavior = false;
            // 
            // Form_KHDangKy
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(760, 472);
            this.Controls.Add(this.gbxTBGa);
            this.Controls.Add(this.btnNext);
            this.Controls.Add(this.groupBox3);
            this.Controls.Add(this.btnOK);
            this.Controls.Add(this.txtMSTB);
            this.Controls.Add(this.label1);
            this.Name = "Form_KHDangKy";
            this.Text = "MỜI QUÝ KHÁCH CHỌN TUYẾN BAY & CHUYẾN BAY";
            this.Load += new System.EventHandler(this.Form_DangKy_Load);
            this.groupBox3.ResumeLayout(false);
            this.gbxTBGa.ResumeLayout(false);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TextBox txtMSTB;
        private System.Windows.Forms.Button btnOK;
        private System.Windows.Forms.GroupBox groupBox3;
        private System.Windows.Forms.Button btnNext;
        private System.Windows.Forms.ListView lvTBGa;
        private System.Windows.Forms.GroupBox gbxTBGa;
        private System.Windows.Forms.ListView lvCBDaChon;
    }
}