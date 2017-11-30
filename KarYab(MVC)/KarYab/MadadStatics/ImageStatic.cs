using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Drawing;
using System.Drawing.Imaging;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices;
using System.Web;

namespace KarYab.MadadStatics
{
    public static class ImageStatic
    {
        public static byte[] imageToByteArray(System.Drawing.Image imageIn)
        {
            MemoryStream ms = new MemoryStream();
            imageIn.Save(ms, System.Drawing.Imaging.ImageFormat.Gif);
            return ms.ToArray();
        }

        public static Image byteArrayToImage(byte[] byteArrayIn)
        {
            MemoryStream ms = new MemoryStream(byteArrayIn);
            //Image returnImage = Image.FromStream(ms);
            //TypeConverter tc = TypeDescriptor.GetConverter(typeof(Bitmap));
            Image Image = (Image)Bitmap.FromStream(ms,true,true);
            return Image;
            // bitmap;
            //byte[] buffer = byteArrayIn;
            //var bitmap = new Bitmap(10, 10, PixelFormat.Format32bppArgb);
            //var bitmap_data = bitmap.LockBits(new Rectangle(0, 0, bitmap.Width, bitmap.Height), ImageLockMode.WriteOnly, PixelFormat.Format32bppArgb);
            //Marshal.Copy(buffer, 0, bitmap_data.Scan0, buffer.Length);
            //bitmap.UnlockBits(bitmap_data);
            //var result = bitmap as Image;
            //return result;
        }
    }
}