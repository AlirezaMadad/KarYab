using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Mvc;
using KarYab.Models;
namespace KarYab.Controllers
{
    public class ImageController : Controller
    {
        //
        // GET: /Image/

        public FileContentResult GetImage(long HumanId)
        {
            try
            {
                using (KARYABDBEntities db = new KARYABDBEntities())
                {
                    byte[] image = db.IMAGES.FirstOrDefault(q => q.HUMANID == HumanId).IMAGE1;
                    if (image != null)
                    {
                        string contentType = "image/png";
                        return File(image, contentType);
                    }
                    return null;
                }
               
            }
            catch (Exception e)
            {
                return null;
            }
            
        }
	}
}