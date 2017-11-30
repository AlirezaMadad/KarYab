using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;
using KarYab.Models;
using System.Drawing;
using KarYab.MadadStatics;
using System.Text;

namespace KarYab.Controllers
{
    public class ImageApiController : ApiController
    {
        [System.Web.Http.AcceptVerbs("GET", "POST")]
        [System.Web.Http.HttpGet]
        public long SetImage(long HumanID,ImageForTransfer Image)//IMAGE image)//long HumanID , byte[] Image)
        {
            try
            {
                using (KARYABDBEntities db = new KARYABDBEntities())
                {
                    var _Image = db.IMAGES.Where(q => q.HUMANID == HumanID).FirstOrDefault();
                    if (_Image != null)
                    {
                        _Image.IMAGE1 = Convert.FromBase64String(Image.Bitmap);
                        db.SaveChanges();
                        return _Image.ID;
                    }
                    else
                    {
                        IMAGE image = new IMAGE();
                        image.HUMANID = HumanID;
                        image.IMAGE1 = Convert.FromBase64String(Image.Bitmap);
                        db.IMAGES.Add(image);
                        db.SaveChanges();
                        return image.ID;

                    }
                  
                }
            }
            catch (Exception e)
            {
                return 0;
            }
            
        }

    }
}
